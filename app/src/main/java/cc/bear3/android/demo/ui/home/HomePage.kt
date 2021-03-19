package cc.bear3.android.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.data.ItemMenu
import cc.bear3.android.demo.databinding.PageHomeBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.base.BaseFragment
import cc.bear3.android.demo.ui.common.MenuListFragment
import cc.bear3.android.demo.ui.todo.TodoFragment
import cc.bear3.android.demo.util.viewpager.ViewPagerAdapter

/**
 *
 * @author TT
 * @since 2020-12-8
 */
class HomePage : BaseActivity() {
    private lateinit var binding: PageHomeBinding

    private val adapter by lazy {
        ViewPagerAdapter<BaseFragment>(supportFragmentManager)
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = PageHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter.setFragments(
            listOf("", ""),
            listOf(MenuListFragment.newInstance(ItemMenu.App, false), TodoFragment.newInstance())
        )

        binding.viewPager.adapter = adapter
    }
}