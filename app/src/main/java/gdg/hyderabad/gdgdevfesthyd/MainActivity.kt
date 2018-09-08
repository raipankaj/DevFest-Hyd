package gdg.hyderabad.gdgdevfesthyd

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

private const val SITE_URL = "https://devfesthyd18.firebaseapp.com/"

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wvPage.loadUrl(SITE_URL)
        wvPage.settings.javaScriptEnabled = true;
        wvPage.settings.javaScriptCanOpenWindowsAutomatically = true;

    }
}
