package com.example.seedrapp.network
import com.example.seedrapp.model.SeedrFile
import okhttp3.*
import org.json.JSONObject

class SeedrClient {
    private val client = OkHttpClient.Builder().cookieJar(CookieStore()).build()
    fun getFiles(): List<SeedrFile> {
        val res = client.newCall(Request.Builder().url("https://www.seedr.cc/rest/files").build()).execute()
        val body = res.body?.string() ?: return emptyList()
        val json = JSONObject(body)
        val list = mutableListOf<SeedrFile>()
        val files = json.getJSONArray("files")
        for (i in 0 until files.length()) {
            val o = files.getJSONObject(i)
            list.add(SeedrFile(o.getString("id"), o.getString("name"), "file", 0,
                o.optString("url"), o.optString("download_url")))
        }
        return list
    }
}
