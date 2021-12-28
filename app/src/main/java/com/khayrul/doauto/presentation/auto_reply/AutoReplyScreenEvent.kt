package com.khayrul.doauto.presentation.auto_reply

import androidx.compose.ui.focus.FocusState
import com.khayrul.doauto.domain.model.AutoReplyRule

sealed class AutoReplyScreenEvent {
    data class AddRule(val rule: AutoReplyRule): AutoReplyScreenEvent()
    data class DeleteRule(val rule: AutoReplyRule): AutoReplyScreenEvent()
    data class EnteredTitle(val value: String): AutoReplyScreenEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AutoReplyScreenEvent()
    data class EnteredReplyMessage(val value: String): AutoReplyScreenEvent()
    data class ChangeReplyMessageFocus(val focusState: FocusState): AutoReplyScreenEvent()
    object ShowAddWindow: AutoReplyScreenEvent()
    object HideAddWindow: AutoReplyScreenEvent()
    object ShowAddingRuleWindow: AutoReplyScreenEvent()
    object HideAddingRuleWindow: AutoReplyScreenEvent()
}
