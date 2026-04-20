package com.example.seedrapp.utils
import android.content.*
import android.net.Uri
object MXPlayerHelper {
    fun play(context: Context, url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.setDataAndType(Uri.parse(url), "video/*")
        i.setPackage("com.mxtech.videoplayer.ad")
        context.startActivity(i)
    }
}
