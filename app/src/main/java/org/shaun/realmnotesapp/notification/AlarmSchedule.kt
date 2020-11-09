package org.shaun.realmnotesapp.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.shaun.realmnotesapp.MainActivity
import org.shaun.realmnotesapp.R

class AlarmSchedule : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val contentIntent = Intent(context!!, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 101, contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val content = intent?.getStringExtra("content")
        val title = intent?.getStringExtra("title")
        val builder = NotificationCompat.Builder(

            context,
            context.getString(R.string.channelName)
        )
            .setOnlyAlertOnce(true)

            .setSmallIcon(R.drawable.reminder_icon)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(101, builder.build())
    }


}