package cc.bear3.android.demo.ui.todo

import android.os.Bundle
import cc.bear3.android.demo.databinding.FragmentTodoBinding
import cc.bear3.android.demo.ui.base.BaseFragment

/**
 *
 * @author TT
 * @since 2020-12-8
 */
class TodoFragment : BaseFragment<FragmentTodoBinding>(FragmentTodoBinding::inflate) {
    override fun initView(savedInstanceState: Bundle?) {

    }

    companion object {
        fun newInstance(): TodoFragment {
            return TodoFragment()
        }
    }
}