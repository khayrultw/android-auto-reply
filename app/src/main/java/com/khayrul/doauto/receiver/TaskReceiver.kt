package com.khayrul.doauto.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.khayrul.doauto.core.constants.Constants
import com.khayrul.doauto.util.NotificationUtils

class TaskReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(Constants.TAG, "Got task from Alarm Manager")
        if (context != null) {
            NotificationUtils.createNotification(
                context = context,
                title = "Alarm Manager",
                text = "Testing Alarm Manager"
            )
        }
    }
}