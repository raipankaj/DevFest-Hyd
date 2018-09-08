package gdg.hyderabad.gdgdevfesthyd.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
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
