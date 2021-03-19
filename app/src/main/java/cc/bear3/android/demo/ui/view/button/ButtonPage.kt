package cc.bear3.android.demo.ui.view.button

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim

/**
 *
 * @author TT
 * @since 2021-3-3
 */
class ButtonPage : BaseActivity() {
    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.page_button, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, ButtonPage::class.java)
            context.startWithAnim(intent)
        }
    }
}