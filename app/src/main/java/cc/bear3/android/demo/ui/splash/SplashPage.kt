package cc.bear3.android.demo.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.app.ActivityStackManager
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.home.HomePage

/**
 *
 * @author TT
 * @since 2021-5-24
 */
class SplashPage : BaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        if (ActivityStackManager.isAppRunning()) {
            finish()
        } else {
            HomePage.invoke(this)
        }

        finish()
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.page_splash, container, false)
    }

    override fun onBackPressed() {

    }
}