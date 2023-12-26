package com.puzzle_agency.androidknowledge.knowledge.util

import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings

object AndroidActivityOpener {

    fun openEmail(context: Context) {
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

    fun openNotificationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        context.startActivity(intent)
    }

    fun openUrl(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }
}
