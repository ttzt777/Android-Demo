package cc.bear3.android.demo.ui.view.button

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cc.bear3.android.demo.databinding.PageButtonBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim

/**
 *
 * @author TT
 * @since 2021-3-3
 */
class ButtonPage : BaseActivity<PageButtonBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, ButtonPage::class.java)
            context.startWithAnim(intent)
        }
    }
}