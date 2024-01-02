package com.puzzle_agency.androidknowledge.knowledge.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.RemoteMessage
import com.puzzle_agency.androidknowledge.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val NOTIFICATION_CHANNEL_EVENT_ID = "notification_event_id"
        private const val NOTIFICATION_CHANNEL_EVENT_NAME = "knowledge_notification_channel"
    }

    private val notificationManager: NotificationManager =
        context.getSystemService(NotificationManager::class.java)

    private fun buildNotification(title: String, body: String): Notification =
        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_EVENT_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setColor(ContextCompat.getColor(context, R.color.teal_200))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(body)
            .build()

    fun notifyFromFirebase(message: RemoteMessage) {
        createNotificationChannel()

        val body = message.notification?.body ?: ""
        val title = message.notification?.title ?: ""

        val notification = buildNotification(title, body)

        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_EVENT_ID,
            NOTIFICATION_CHANNEL_EVENT_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

        notificationManager.createNotificationChannel(channel)
    }
}
