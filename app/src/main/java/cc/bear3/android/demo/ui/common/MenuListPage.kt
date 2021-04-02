package cc.bear3.android.demo.ui.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.data.ItemMenu
import cc.bear3.android.demo.databinding.PageMenuListBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.common.MenuListFragment.Companion.ARG_CAN_BACK
import cc.bear3.android.demo.ui.common.MenuListFragment.Companion.ARG_MENU_TARGET
import cc.bear3.android.demo.util.context.startWithAnim

/**
 *
 * @author TT
 * @since 2020-12-4
 */
class MenuListPage : BaseActivity() {

    private var target: ItemMenu? = null
    private var canBack: Boolean = true

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = PageMenuListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        initParams(savedInstanceState) {
            target = it.getSerializable(ARG_MENU_TARGET) as ItemMenu?
            canBack = it.getBoolean(ARG_CAN_BACK, canBack)
        }

        target?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, MenuListFragment.newInstance(it, canBack))
                .commitAllowingStateLoss()
        }
    }

    override fun getTagName(): String {
        return "${MenuListPage::class.java.simpleName} - ${target?.name ?: ""}"
    }

    companion object {
        fun invoke(context: Context, target: ItemMenu, canBack: Boolean = true) {
            val intent = Intent(context, MenuListPage::class.java).apply {
                putExtra(ARG_MENU_TARGET, target)
                putExtra(ARG_CAN_BACK, canBack)
            }
            context.startWithAnim(intent)
        }
    }
}