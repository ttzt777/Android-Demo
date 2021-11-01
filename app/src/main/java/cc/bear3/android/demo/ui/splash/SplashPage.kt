package cc.bear3.android.demo.ui.splash

import android.os.Bundle
import cc.bear3.android.demo.app.ActivityStackManager
import cc.bear3.android.demo.databinding.PageSplashBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.home.HomePage

/**
 *
 * @author TT
 * @since 2021-5-24
 */
class SplashPage : BaseActivity<PageSplashBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        if (ActivityStackManager.isAppRunning()) {
            finish()
        } else {
            HomePage.invoke(this)
        }

        finish()
    }

    override fun onBackPressed() {

    }
}