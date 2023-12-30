package com.puzzle_agency.androidknowledge.knowledge.util

import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings

object AndroidIntentOpener {
    /**
     * This function opens email app with intent chooser
     * Add this queries block to manifest
     *     <activity ...>
     *
     *     <queries>
     *         <intent>
     *             <action android:name="android.intent.action.VIEW" />
     *             <data android:scheme="mailto" />
     *         </intent>
     *     </queries>
     */
    fun openChooseEmailApp(context: Context) {
        // Add mailto uri to only show mail apps
        val emailIntent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"))

        val pm: PackageManager = context.packageManager

        val resInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pm.queryIntentActivities(emailIntent, PackageManager.ResolveInfoFlags.of(0L))
        } else {
            pm.queryIntentActivities(emailIntent, 0)
        }

        if (resInfo.isNotEmpty()) {
            var ri = resInfo[0]

            val intentChooser = pm.getLaunchIntentForPackage(ri.activityInfo.packageName)
            val openInChooser =
                Intent.createChooser(intentChooser, "Choose email app")

            val intentList: MutableList<LabeledIntent> = ArrayList()
            for (i in 1 until resInfo.size) {
                ri = resInfo[i]
                val packageName = ri.activityInfo.packageName
                val intent = pm.getLaunchIntentForPackage(packageName)
                intentList.add(LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon))
            }
            val extraIntents = intentList.toTypedArray()
            openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents)

            context.startActivity(openInChooser)
        }
    }

    /**
     * Opens send email form with the optional content
     */
    fun openSendEmailForm(
        context: Context,
        mailTo: String? = null,
        subject: String? = null,
        body: String? = null
    ) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")

            mailTo?.let { putExtra(Intent.EXTRA_EMAIL, arrayOf(it)) }
            subject?.let { putExtra(Intent.EXTRA_SUBJECT, it) }
            body?.let { putExtra(Intent.EXTRA_TEXT, it) }
        }

        context.startActivity(emailIntent)
    }

    fun openNotificationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        context.startActivity(intent)
    }

    fun openUrl(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

    fun openDialNumber(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }

    fun shareString(context: Context, string: String, title: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, string)
        context.startActivity(Intent.createChooser(shareIntent, title))
    }

    fun shareUri(context: Context, uri: Uri, title: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

        context.startActivity(Intent.createChooser(shareIntent, title))
    }
}
