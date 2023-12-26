package com.puzzle_agency.androidknowledge.knowledge.util

import android.content.Context
import android.content.Intent
import android.net.Uri

object AndroidShareManager {

    fun shareString(string: String, title: String, context: Context) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, "Share")
        i.putExtra(Intent.EXTRA_TEXT, string)
        context.startActivity(Intent.createChooser(i, title))
    }

    fun shareUri(uri: Uri, title: String, context: Context) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

        context.startActivity(Intent.createChooser(shareIntent, title))
    }
}