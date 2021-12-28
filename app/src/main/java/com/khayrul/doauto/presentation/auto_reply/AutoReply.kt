package com.khayrul.doauto.presentation.auto_reply

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khayrul.doauto.R
import com.khayrul.doauto.core.constants.Constants
import com.khayrul.doauto.core.preference.PreferencesManager
import com.khayrul.doauto.data.data_source.DoautoDatabase
import com.khayrul.doauto.data.repository.AutoReplyRuleRepoImpl
import com.khayrul.doauto.domain.model.AutoReplyRule
import com.khayrul.doauto.presentation.auto_reply.component.HintTextField
import com.khayrul.doauto.presentation.auto_reply.component.StatusConstraintSection
import com.khayrul.doauto.util.NotificationUtils

@Composable
fun AutoReply() {
    val context = LocalContext.current
    val preferencesManager = PreferencesManager.getInstance(context)
    val checkedState =
        remember { mutableStateOf(preferencesManager.isAutoReplyServiceEnabled()) }

    val autoReplyRuleRepo = AutoReplyRuleRepoImpl(
        DoautoDatabase.getInstance(context.applicationContext as Application).autoReplyRuleDao
    )

    val viewModel = AutoReplyViewModel.getInstance(autoReplyRuleRepo)

    val title = viewModel.title.value
    val replyMessage = viewModel.replyMessage.value
    val rules = viewModel.rules.value
    val addWindow = viewModel.addWindow.value
    val addingRule = viewModel.addingRule.value

    Log.d(Constants.TAG, "Hello: $addWindow")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Auto Reply Service")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = checkedState.value,
                    onCheckedChange = {
                        checkedState.value = it
                        when (it) {
                            true -> preferencesManager.enableAutoReplyService()
                            false -> preferencesManager.disableAutoReplyService()
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = {
                    NotificationUtils.stopForegroundTestService(context)
                }
            ) {
                Text(text = "Stop Service")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = {
                    NotificationUtils.startForegroundTestService(context)
                }
            ) {
                Text(text = "Start Service in background")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(0.7f),
                onClick = {
                    NotificationUtils.createNotification(
                        context = context,
                        title = "Awesome",
                        text = "Awesome Notification"
                    )
                }
            ) {
                Text(text = "Create Notification")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    items(rules) { rule ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp)
                                .clickable {

                                },
                            elevation = 4.dp
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp)
                                        .padding(end = 32.dp)
                                ) {
                                    Text(
                                        text = "Title: ${rule.title}",
                                        fontSize = 18.sp,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = "Message: ${rule.message}")
                                }
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.align(Alignment.BottomEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete word"
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FloatingActionButton(
                        onClick = { viewModel.onEvent(AutoReplyScreenEvent.ShowAddWindow)}
                    ) {
                        Icon(Icons.Filled.Add, "")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        if (addWindow) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { viewModel.onEvent(AutoReplyScreenEvent.HideAddWindow) }
                    .background(colorResource(R.color.windowBg))
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.6f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    FloatingActionButton(onClick = { viewModel.onEvent(AutoReplyScreenEvent.ShowAddingRuleWindow) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    FloatingActionButton(onClick = { viewModel.onEvent(AutoReplyScreenEvent.ShowAddingRuleWindow) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "")
                    }
                }
            }
        }
        if(addingRule) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.grey200))
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HintTextField(
                        text = title.text,
                        hint = title.hint,
                        onValueChange = {
                            viewModel.onEvent(AutoReplyScreenEvent.EnteredTitle(it))
                        },
                        textStyle = MaterialTheme.typography.h6,
                        onFocusChange = {
                            viewModel.onEvent(AutoReplyScreenEvent.ChangeTitleFocus(it))
                        },
                        singleLine = true,
                        isHintVisible = title.isHintVisible && title.text == "",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HintTextField(
                        text = replyMessage.text,
                        hint = replyMessage.hint,
                        onValueChange = {
                            viewModel.onEvent(AutoReplyScreenEvent.EnteredReplyMessage(it))
                        },
                        textStyle = MaterialTheme.typography.body1,
                        onFocusChange = {
                            viewModel.onEvent(AutoReplyScreenEvent.ChangeReplyMessageFocus(it))
                        },
                        isHintVisible = replyMessage.isHintVisible && replyMessage.text == "",

                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(100.dp, 150.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    StatusConstraintSection()
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.Bottom),
                        onClick = { viewModel.onEvent(AutoReplyScreenEvent.HideAddingRuleWindow) }
                    ) {
                        Text(text = "Cancel", fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.Bottom),
                        onClick = {
                            viewModel.onEvent(
                                AutoReplyScreenEvent.AddRule(
                                    AutoReplyRule(
                                        title = title.text,
                                        message = replyMessage.text
                                    )
                                )
                            )
                        }
                    ) {
                        Text(text = "Save", fontSize = 11.sp)
                    }
                }
            }

        }
    }
}