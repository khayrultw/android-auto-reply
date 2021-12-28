package com.khayrul.doauto.service

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.khayrul.doauto.core.constants.Constants
import com.khayrul.doauto.core.preference.PreferencesManager
import com.khayrul.doauto.util.NotificationUtils
import java.lang.Exception

class NotificationListener : NotificationListenerService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {

        super.onNotificationPosted(sbn)

        val notificationExt = NotificationUtils.getWearableNotification(sbn) ?: return

        Log.d(Constants.TAG, "name: ${notificationExt.name} Tag: ${notificationExt.tag} }")

        if(!PreferencesManager.getInstance(this).isAutoReplyServiceEnabled()) {
            return
        }

        val _title = notificationExt.bundle?.getString("android.title") ?: ""

        if(_title.contains("Khayrul")) {
            val bundle = Bundle()
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val title = notificationExt.bundle?.getString("android.title") ?: ""
            val text = notificationExt.bundle?.getString("android.text") ?: ""
            val reply = if (text.contains("(hello)|(hi)".toRegex()) ) "Hi $title" else "okay"
            bundle.putCharSequence(notificationExt.remoteInputs[0].resultKey, reply)
            RemoteInput.addResultsToIntent(notificationExt.remoteInputs.toTypedArray(), intent, bundle)

            try {
                notificationExt.pendingIntent?.let {
                    it.send(this, 0, intent)
                    cancelNotification(sbn?.key)
                    Log.d(Constants.TAG, "Hello Success")
                }
            } catch (e: Exception) {
                Log.d(Constants.TAG, "Hello exception")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constants.TAG, "Destroyed")
    }
}