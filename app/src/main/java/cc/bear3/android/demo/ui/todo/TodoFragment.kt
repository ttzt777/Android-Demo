package cc.bear3.android.demo.ui.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.ui.base.BaseFragment

/**
 *
 * @author TT
 * @since 2020-12-8
 */
class TodoFragment : BaseFragment() {
    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    companion object {
        fun newInstance(): TodoFragment {
            return TodoFragment()
        }
    }
}