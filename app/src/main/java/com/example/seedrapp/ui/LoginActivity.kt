package com.example.seedrapp.ui
import android.content.*
import android.os.*
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val web = WebView(this)
        setContentView(web)
        web.settings.javaScriptEnabled = true
        web.webViewClient = object: WebViewClient(){
            override fun onPageFinished(v: WebView?, url: String?){
                if(url?.contains("seedr.cc/files")==true){
                    startActivity(Intent(this@LoginActivity, FileBrowserActivity::class.java))
                    finish()
                }
            }
        }
        web.loadUrl("https://www.seedr.cc")
    }
}
