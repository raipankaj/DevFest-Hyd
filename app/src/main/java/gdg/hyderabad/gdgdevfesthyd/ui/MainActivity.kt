package gdg.hyderabad.gdgdevfesthyd.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import gdg.hyderabad.gdgdevfesthyd.R
import gdg.hyderabad.gdgdevfesthyd.utils.AppConstants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mIsFirstTimeLoaded = false
    private var mLoadProgressIndicator = true

    private val mShortcutIcons by lazy {
        arrayOf(R.drawable.shortcut_gallery, R.drawable.shortcut_schedule, R.drawable.shortcut_join_gdg)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createAppShortcuts()

        val shortcutUrl = intent?.getStringExtra(AppConstants.Shortcuts.SHORTCUT_URL)
                ?: AppConstants.LAUNCH_URL

        wvPage.loadUrl(shortcutUrl)
        wvPage.webViewClient = mWebViewClient
        wvPage.webChromeClient = mChromeWebClient
        wvPage.settings.javaScriptEnabled = true
        wvPage.settings.javaScriptCanOpenWindowsAutomatically = true
    }

    //Add firebase support later, to get the dynamic list of shortcuts with icons
    private fun createAppShortcuts() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            val shortcutManager = getSystemService(ShortcutManager::class.java)
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            intent.action = Intent.ACTION_VIEW

            val shortcutsLabels = resources.getStringArray(R.array.shortcuts_key)
            val shortcutsUrl = resources.getStringArray(R.array.shortcuts_key_url)
            val shortcutArrayList = ArrayList<ShortcutInfo>()

            //Do not remove condition especially when data will come from firebase
            if (shortcutsLabels.size == shortcutsUrl.size) {
                for (counter in 0 until shortcutsLabels.size) {
                    intent.putExtra(AppConstants.Shortcuts.SHORTCUT_URL, shortcutsUrl[counter])

                    val shortcutInfo = ShortcutInfo
                            .Builder(this@MainActivity, AppConstants.Shortcuts.SHORTCUT_BUILDER_KEY.plus(shortcutsLabels[counter]))
                            .setIcon(Icon.createWithResource(this@MainActivity, mShortcutIcons[counter]))
                            .setShortLabel(shortcutsLabels[counter])
                            .setLongLabel(shortcutsLabels[counter])
                            .setIntent(intent)
                            .build()
                    shortcutArrayList.add(shortcutInfo)
                }
            }

            shortcutManager.dynamicShortcuts = shortcutArrayList
        }
    }

    /**
     * Keep track on the progress on the page, in case progress reached to maximum(100)
     * hide the progress bar and also stop progressive animation
     */
    private val mChromeWebClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            if (newProgress > 80) {
                pbLoading.visibility = View.GONE
                pbLoading.progressiveStop()
            }
        }
    }

    /**
     * Enable the in web view browsing and also when page starts loading start displaying the
     * animation to the user.
     */
    private val mWebViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            if (mLoadProgressIndicator) {
                pbLoading.visibility = View.VISIBLE
                pbLoading.progressiveStart()
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            wvPage.loadUrl(request?.url?.toString())
            mLoadProgressIndicator = true
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            if (mIsFirstTimeLoaded.not()) {
                Handler().postDelayed({ flLoading.visibility = View.GONE }, 1500)
                mIsFirstTimeLoaded = true
            }
        }
    }

    /**
     * Until and less there are pages left to traversed do not kill the app. Let the user to
     * navigate back until no more page left in the web view to navigate further.
     */
    override fun onBackPressed() {
        if (wvPage.canGoBack()) {
            wvPage.goBack()
            mLoadProgressIndicator = false
        } else {
            finish()
        }
    }
}
