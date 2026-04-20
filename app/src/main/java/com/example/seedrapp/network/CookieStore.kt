package com.example.seedrapp.network

import android.webkit.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class CookieStore : CookieJar {

    override fun loadForRequest(url: HttpUrl): List<Cookie> {

        val cookieManager = CookieManager.getInstance()

        val cookieString = cookieManager.getCookie(url.toString())
            ?: cookieManager.getCookie("https://www.seedr.cc")
            ?: return emptyList()

        return cookieString.split(";").mapNotNull {
            Cookie.parse(url, it.trim())
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        // WebView already manages cookies
    }
}
