package com.example.seedrapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.addJavascriptInterface(JSBridge(), "Android")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {

                if (url?.contains("seedr.cc/files") == true) {

                    injectExtractor()
                }
            }
        }

        webView.loadUrl("https://www.seedr.cc")
    }

    private fun injectExtractor() {

        val js = """
            (function() {
                let results = [];
                
                document.querySelectorAll("a").forEach(el => {
                    if (el.href && (el.href.includes("download") || el.href.includes("stream"))) {
                        results.push({
                            name: el.innerText || "file",
                            url: el.href
                        });
                    }
                });

                Android.onFilesExtracted(JSON.stringify(results));
            })();
        """

        webView.evaluateJavascript(js, null)
    }

    inner class JSBridge {

        @JavascriptInterface
        fun onFilesExtracted(json: String) {
            runOnUiThread {
                try {
                    val arr = JSONArray(json)
                    Toast.makeText(this@MainActivity, "Found ${arr.length()} files", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
