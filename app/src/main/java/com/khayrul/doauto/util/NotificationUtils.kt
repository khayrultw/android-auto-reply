package com.khayrul.doauto.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.khayrul.doauto.R
import com.khayrul.doauto.core.constants.Constants
import com.khayrul.doauto.domain.model.WearableNotification
import com.khayrul.doauto.service.ForegroundNotificationService
import com.khayrul.doauto.service.NotificationListener

object NotificationUtils {
    fun createNotificationChannel(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun startForegroundTestService(context: Context) {
        askNotificationListenerPerm(context)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(context, ForegroundNotificationService::class.java)
            context.startForegroundService(intent)
        }
    }

    fun stopForegroundTestService(context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(context, ForegroundNotificationService::class.java)
            context.stopService(intent)

            val intentListener = Intent(context, NotificationListener::class.java)
            context.stopService(intentListener)
        }
    }


    fun createNotification(context: Context, title: String, text: String) {
        val notification = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.like)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, notification)
    }

    private fun notificationListenerEnabled(context: Context): Boolean {
        val cn = ComponentName(context, NotificationListener::class.java)
        val flat = Settings.Secure.getString(
            context.contentResolver,
            "enabled_notification_listeners"
        )
        return flat != null && flat.contains(cn.flattenToString())
    }

    private fun askNotificationListenerPerm(context: Context) {
        if(notificationListenerEnabled(context)) {
            Log.d(Constants.TAG, "Enabled")
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            context.startActivity(intent)
        }
    }

    fun getWearableNotification(sbn: StatusBarNotification?): WearableNotification? {
        return sbn?.notification?.let { notification ->
            val wearableExt = NotificationCompat.WearableExtender(notification)

            val actions = notification.actions ?: return null

            val remoteInputs = ArrayList<android.app.RemoteInput>(actions.size)
            var pendingIntent: PendingIntent? = null

            for(action in actions) {
                action?.remoteInputs?.let { _remoteInputs ->
                    for(remoteInput in _remoteInputs) {
                        remoteInputs.add(remoteInput as android.app.RemoteInput)
                        Log.d(Constants.TAG, "Found an RemoteInput")
                        pendingIntent = action.actionIntent
                    }
                }
            }
            WearableNotification(
                sbn.tag,
                sbn.packageName,
                pendingIntent,
                remoteInputs.toList(),
                notification.extras
            )
        }
    }
}