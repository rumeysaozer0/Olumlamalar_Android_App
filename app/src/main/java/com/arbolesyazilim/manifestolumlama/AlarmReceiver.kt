package com.arbolesyazilim.manifestolumlama

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    private val manifestList = Constants.getManifests()
    override fun onReceive(context: Context, intent: Intent) {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        // Saat 7 ile 22 arasında ise bildirim göster
        if (currentHour in 7..24) {
            showNotification(context)
        }
    }

    private fun showNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Bildirim kanalını oluştur
        createNotificationChannel(notificationManager)
        val randomIndex = (0 until manifestList.size).random()
        val secilenManifest = manifestList[randomIndex].word
        // Bildirim oluştur
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Manifest")
            .setContentText(secilenManifest)
            .setSmallIcon(R.drawable.star)
            .build()

        // Bildirimi göster
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Uygulama Bildirimleri",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "uygulama_bildirimleri"
        private const val NOTIFICATION_ID = 1

        fun setAlarm(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                PendingIntent.getBroadcast(context, 0, intent, 0)
            }

            // 1 saatte bir tekrarlayan alarm
            val repeatInterval = AlarmManager.INTERVAL_HOUR
            val triggerTime: Long = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 7) // Sabah 7'de başla
            }.timeInMillis

            // Alarmı ayarla
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                repeatInterval,
                alarmIntent
            )
        }

    }
}
