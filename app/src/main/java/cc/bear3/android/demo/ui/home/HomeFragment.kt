package cc.bear3.android.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.data.ItemMenu
import cc.bear3.android.demo.databinding.FragmentHomeBinding
import cc.bear3.android.demo.ui.base.BaseFragment
import cc.bear3.android.demo.ui.common.MenuListFragment
import cc.bear3.android.demo.ui.todo.TodoFragment
import cc.bear3.android.util.viewpager.ViewPagerAdapter

/**
 *
 * @author TT
 * @since 2020-12-8
 */
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding

    private val adapter by lazy {
        ViewPagerAdapter<BaseFragment>(childFragmentManager)
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setFragments(
            listOf("", ""),
            listOf(MenuListFragment.newInstance(ItemMenu.App, false), TodoFragment.newInstance())
        )

        binding.viewPager.adapter = adapter
    }
}