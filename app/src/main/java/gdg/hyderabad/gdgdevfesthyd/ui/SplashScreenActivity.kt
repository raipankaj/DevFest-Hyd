package gdg.hyderabad.gdgdevfesthyd.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Add animated splash screen here, thereafter remove windowbackground from styles.xml
 */
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }
}