package com.example.seedrapp.network

import com.example.seedrapp.model.SeedrFile
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class SeedrClient {

    private val client = OkHttpClient.Builder()
        .cookieJar(CookieStore())
        .build()

    fun getFiles(folderId: String = "root"): List<SeedrFile> {

        val url = if (folderId == "root") {
            "https://www.seedr.cc/rest/files"
        } else {
            "https://www.seedr.cc/rest/folder/$folderId"
        }

        return try {

            val response = client.newCall(Request.Builder().url(url).build()).execute()
            val body = response.body?.string() ?: return emptyList()

            // 🚨 critical fix: avoid crash if not logged in
            if (!body.trim().startsWith("{")) {
                return emptyList()
            }

            val json = JSONObject(body)

            val list = mutableListOf<SeedrFile>()

            val folders = json.optJSONArray("folders")
            val files = json.optJSONArray("files")

            if (folders != null) {
                for (i in 0 until folders.length()) {
                    val o = folders.getJSONObject(i)
                    list.add(
                        SeedrFile(
                            id = o.getString("id"),
                            name = o.getString("name"),
                            type = "folder",
                            size = 0,
                            streamUrl = null,
                            downloadUrl = null
                        )
                    )
                }
            }

            if (files != null) {
                for (i in 0 until files.length()) {
                    val o = files.getJSONObject(i)
                    list.add(
                        SeedrFile(
                            id = o.getString("id"),
                            name = o.getString("name"),
                            type = "file",
                            size = o.optLong("size", 0),
                            streamUrl = o.optString("url", null),
                            downloadUrl = o.optString("download_url", null)
                        )
                    )
                }
            }

            list

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
