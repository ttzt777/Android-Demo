package cc.bear3.android.demo.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cc.bear3.android.demo.data.ItemMenu
import cc.bear3.android.demo.databinding.PageHomeBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.base.BaseFragment
import cc.bear3.android.demo.ui.common.MenuListFragment
import cc.bear3.android.demo.ui.todo.TodoFragment
import cc.bear3.android.demo.util.context.startWithAnim
import cc.bear3.android.demo.util.viewpager.ViewPagerAdapter

/**
 *
 * @author TT
 * @since 2020-12-8
 */
class HomePage : BaseActivity<PageHomeBinding>(PageHomeBinding::inflate) {
    private val adapter by lazy {
        ViewPagerAdapter<BaseFragment>(supportFragmentManager)
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter.setFragments(
            listOf("", ""),
            listOf(MenuListFragment.newInstance(ItemMenu.App, false), TodoFragment.newInstance())
        )

        binding.viewPager.adapter = adapter
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, HomePage::class.java)
            context.startWithAnim(intent)
        }
    }
}