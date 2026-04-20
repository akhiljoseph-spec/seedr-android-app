package com.example.seedrapp.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri

object DownloadHelper {

    fun download(context: Context, url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }
}
