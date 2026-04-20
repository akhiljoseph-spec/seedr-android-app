package com.example.seedrapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object PlayerHelper {

    fun play(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "video/*")
        intent.setPackage("com.mxtech.videoplayer.ad")
        context.startActivity(intent)
    }
}
