package com.khayrul.doauto.presentation.auto_reply

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khayrul.doauto.core.constants.Constants
import com.khayrul.doauto.domain.model.AutoReplyRule
import com.khayrul.doauto.domain.repository.AutoReplyRuleRepo
import kotlinx.coroutines.launch

class AutoReplyViewModel
private constructor(private val autoReplyRuleRepo: AutoReplyRuleRepo): ViewModel() {

    private val _rules = mutableStateOf<List<AutoReplyRule>>(emptyList())
    val rules: State<List<AutoReplyRule>> = _rules

    private val _title = mutableStateOf(TextFieldState(
        hint = "Enter Title"
    ))
    val title: State<TextFieldState> = _title

    private val _replyMessage = mutableStateOf(TextFieldState(
        hint = "Enter reply message"
    ))
    val replyMessage: State<TextFieldState> = _replyMessage

    private val _addWindow = mutableStateOf(false)
    val addWindow: State<Boolean> = _addWindow

    private val _addingRule = mutableStateOf(false)
    val addingRule: State<Boolean> = _addingRule

    init {
        viewModelScope.launch {
            Log.d(Constants.TAG, "Hello from init: ${autoReplyRuleRepo.getRules().size}")
            val _arr = mutableListOf<AutoReplyRule>()
            autoReplyRuleRepo.getRules().forEach { rule ->
                _arr.add(rule)
            }
            _rules.value = _arr
        }
    }

    fun onEvent(event: AutoReplyScreenEvent) {
        when (event) {
            is AutoReplyScreenEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }
            is AutoReplyScreenEvent.EnteredReplyMessage -> {
                _replyMessage.value = replyMessage.value.copy(
                    text = event.value
                )
            }
            is AutoReplyScreenEvent.ChangeTitleFocus -> {
                _title.value = title.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }
            is AutoReplyScreenEvent.ChangeReplyMessageFocus -> {
                _replyMessage.value = replyMessage.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }
            is AutoReplyScreenEvent.AddRule -> {
                viewModelScope.launch {
                    autoReplyRuleRepo.addRule(event.rule)
                    val _arr = _rules.value.toMutableList()
                    _arr.add(event.rule)
                    _rules.value = _arr
                }
                _addingRule.value = false
                _addWindow.value = false
            }
            is AutoReplyScreenEvent.DeleteRule -> TODO()
            is AutoReplyScreenEvent.HideAddWindow -> {
                _addWindow.value = false
            }
            is AutoReplyScreenEvent.HideAddingRuleWindow -> {
                _addingRule.value = false
            }
            is AutoReplyScreenEvent.ShowAddWindow -> {
                Log.d(Constants.TAG, "Hello from view model")
                _addWindow.value = true
                Log.d(Constants.TAG, "Hello from view model: " + addWindow.value)
            }
            is AutoReplyScreenEvent.ShowAddingRuleWindow -> {
                _addingRule.value = true
            }
        }
    }

    companion object {
        private var viewModel: AutoReplyViewModel? = null

        fun getInstance(autoReplyRuleRepo: AutoReplyRuleRepo): AutoReplyViewModel {
            if(viewModel == null) {
                viewModel = AutoReplyViewModel(autoReplyRuleRepo)
            }

            return viewModel as AutoReplyViewModel
        }
    }
}