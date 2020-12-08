package cc.bear3.android.demo.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.data.ItemMenu
import cc.bear3.android.demo.databinding.FragmentMenuListBinding
import cc.bear3.android.demo.manager.refresh.RefreshProxy
import cc.bear3.android.demo.ui.base.BaseFragment

/**
 *
 * @author TT
 * @since 2020-12-4
 */
class MenuListFragment : BaseFragment() {

    private var target: ItemMenu? = null
    private var canBack: Boolean = true

    private lateinit var binding: FragmentMenuListBinding

    private val adapter by lazy {
        MenuListAdapter()
    }

    private val refreshProxy by lazy {
        RefreshProxy(
            binding.include.refreshLayout,
            adapter,
            noMore = false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAvailableArgs(savedInstanceState) {
            target = it.getSerializable(ARG_MENU_TARGET) as ItemMenu?
            canBack = it.getBoolean(ARG_CAN_BACK, canBack)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleBar.hasIcon = canBack

        target?.let {

        }
    }

    companion object {
        private const val ARG_MENU_TARGET = "arg_menu_target"
        private const val ARG_CAN_BACK = "arg_can_back"

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