package gdg.hyderabad.gdgdevfesthyd.ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import gdg.hyderabad.gdgdevfesthyd.R
import kotlinx.android.synthetic.main.activity_main.*

private const val SITE_URL = "https://devfesthyd18.firebaseapp.com/"

class MainActivity : AppCompatActivity() {

    private var mIsFirstTimeLoaded = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wvPage.loadUrl(SITE_URL)
        wvPage.webViewClient = mWebViewClient
        wvPage.settings.javaScriptEnabled = true
        wvPage.settings.javaScriptCanOpenWindowsAutomatically = true
    }

    private val mWebViewClient = object: WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            Log.e("Check","url"+url)
            wvPage.loadUrl(url)
            return true
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            if (mIsFirstTimeLoaded.not()) {
                wvPage.visibility = View.VISIBLE
                mIsFirstTimeLoaded = true
            }
        }
    }

    override fun onBackPressed() {
        if (wvPage.canGoBack()) {
            wvPage.goBack()
        } else {
            finish()
        }
    }
}
