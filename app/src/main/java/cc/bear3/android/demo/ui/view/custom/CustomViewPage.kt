package cc.bear3.android.demo.ui.view.custom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cc.bear3.android.demo.databinding.PageCustomviewBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim

/**
 *
 * @author TT
 * @since 2020-12-9
 */
class CustomViewPage : BaseActivity<PageCustomviewBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, CustomViewPage::class.java)
            context.startWithAnim(intent)
        }
    }
}