package com.khayrul.doauto.util

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.app.KeyguardManager
import android.app.NotificationManager
import android.media.AudioManager


object PhoneStatus {

    const val LOCKED = "locked"
    const val CHARGING = "charging"
    const val SILENT = "silent"
    const val DND = "dnd"

    fun isLocked(context: Context): Boolean {
        val myKM = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return myKM.isDeviceLocked
        }
        return myKM.isKeyguardLocked
    }

    fun isCharging(context: Context): Boolean {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return bm.isCharging
        }
        return false
    }

    fun isSilent(context: Context): Boolean {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return am.ringerMode == AudioManager.RINGER_MODE_SILENT
    }

    fun isDND(context: Context): Boolean {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return nm.currentInterruptionFilter != NotificationManager.INTERRUPTION_FILTER_ALL
        }
        return false
    }
}
