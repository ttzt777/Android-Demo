package cc.bear3.android.demo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.bear3.android.demo.R
import com.gyf.immersionbar.ImmersionBar

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setImmersionBar()
    }

    private fun setImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.transparent)
            .statusBarDarkFont(true)
            .init()
    }
}