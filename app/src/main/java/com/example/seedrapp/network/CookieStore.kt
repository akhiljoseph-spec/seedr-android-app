package com.example.seedrapp.network
import android.webkit.CookieManager
import okhttp3.*
class CookieStore : CookieJar {
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val cookies = CookieManager.getInstance().getCookie(url.toString()) ?: return emptyList()
        return cookies.split(";").mapNotNull { Cookie.parse(url, it.trim()) }
    }
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {}
}
