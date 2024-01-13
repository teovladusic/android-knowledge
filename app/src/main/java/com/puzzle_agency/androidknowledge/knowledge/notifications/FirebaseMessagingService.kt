package com.puzzle_agency.androidknowledge.knowledge.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Service that listens to firebase notifications
 * Implementation steps:
 *  1. Make sure you have firebase and google services dependency
 *  2. <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
 *  3. Define service in manifest
 *         <service
 *             android:name=".knowledge.notifications.FirebaseMessagingService"
 *             android:exported="false">
 *             <intent-filter>
 *                 <action android:name="com.google.firebase.MESSAGING_EVENT" />
 *             </intent-filter>
 *         </service>
 *  4. Define metadata for notifications when service is down
 *         <meta-data
 *             android:name="com.google.firebase.messaging.default_notification_icon"
 *             android:resource="@drawable/ic_logo_round" />
 *
 *         <meta-data
 *             android:name="com.google.firebase.messaging.default_notification_color"
 *             android:resource="@color/blue700" />
 */

@AndroidEntryPoint
class FirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onMessageReceived(message: RemoteMessage) {
        notificationHelper.notifyFromFirebase(message)
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
