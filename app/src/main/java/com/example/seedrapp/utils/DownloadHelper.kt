package com.example.seedrapp.utils
import android.app.*
import android.content.*
import android.net.Uri
object DownloadHelper {
    fun download(context: Context, url: String) {
        val req = DownloadManager.Request(Uri.parse(url))
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(req)
    }
}
