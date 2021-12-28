package com.khayrul.doauto.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.khayrul.doauto.presentation.navigation.Navigation
import com.khayrul.doauto.presentation.ui.theme.DoautoTheme
import com.khayrul.doauto.util.NotificationUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationUtils.createNotificationChannel(this)
        setContent {
            DoautoTheme {
                Navigation()
            }
        }
    }
}