package cc.bear3.android.demo.ui.common

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cc.bear3.android.demo.data.ItemMenu
import cc.bear3.android.demo.databinding.FragmentMenuListBinding
import cc.bear3.android.demo.manager.refresh.RefreshProxy
import cc.bear3.android.demo.ui.base.BaseFragment

/**
 *
 * @author TT
 * @since 2020-12-4
 */
class MenuListFragment : BaseFragment<FragmentMenuListBinding>() {

    private var target: ItemMenu? = null
    private var canBack: Boolean = true

    private val refreshProxy by lazy {
        RefreshProxy(
            binding.include.refreshLayout,
            MenuListAdapter(),
            noMore = false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initParams(savedInstanceState) {
            target = it.getSerializable(ARG_MENU_TARGET) as ItemMenu?
            canBack = it.getBoolean(ARG_CAN_BACK, canBack)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        with(binding) {
            titleBar.hasIcon = canBack

            with(include.recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = refreshProxy.adapter
            }

            target?.let {
                titleBar.title = getString(it.stringId)

                refreshProxy.onFinish(dataList = it.createList())
            }
        }
    }

    override fun getTagName(): String {
        return "${MenuListFragment::class.java.simpleName} - ${target?.name ?: ""}"
    }

    companion object {
        internal const val ARG_MENU_TARGET = "arg_menu_target"
        internal const val ARG_CAN_BACK = "arg_can_back"

        fun newInstance(target: ItemMenu, canBack: Boolean = true): MenuListFragment {
            return MenuListFragment().apply {
                arguments = newBundle(target, canBack)
            }
        }

        fun newBundle(target: ItemMenu, canBack: Boolean = true): Bundle {
            return Bundle().apply {
                putSerializable(ARG_MENU_TARGET, target)
                putBoolean(ARG_CAN_BACK, canBack)
            }
        }
    }
}