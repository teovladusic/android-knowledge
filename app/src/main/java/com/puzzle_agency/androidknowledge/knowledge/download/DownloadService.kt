package com.puzzle_agency.androidknowledge.knowledge.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadService @Inject constructor(@ApplicationContext context: Context) {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    fun downloadImage(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Image download")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "image.jpg")

        return downloadManager.enqueue(request)
    }
}
