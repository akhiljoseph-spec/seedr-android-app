package com.example.seedrapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        WebView.setWebContentsDebuggingEnabled(true)

        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {

                if (url?.contains("seedr.cc/files") == true) {

                    // Ensure cookies are saved
                    CookieManager.getInstance().flush()

                    // Delay to ensure session is ready
                    Handler(Looper.getMainLooper()).postDelayed({

                        startActivity(Intent(this@LoginActivity, FileBrowserActivity::class.java))
                        finish()

                    }, 1200)
                }
            }
        }

        webView.loadUrl("https://www.seedr.cc")
    }
}
