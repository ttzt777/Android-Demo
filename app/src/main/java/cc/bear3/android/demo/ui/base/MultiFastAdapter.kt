package cc.bear3.android.demo.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 *
 * @author TT
 * @since 2021-6-2
 */
abstract class MultiFastAdapter<T : IMultiData> : BaseAdapter<T, BindingViewHolder<out ViewBinding>>(){
    final override fun onBindCustomViewHolder(holder: BindingViewHolder<out ViewBinding>, position: Int) {
        convert(holder.binding, getData(position))
    }

    final override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<out ViewBinding> {
        val data = getData(viewType)
        return BindingViewHolder(parent, data.getViewBindingFun())
    }

    final override fun getCustomViewType(position: Int): Int {
        return position
    }

    abstract fun convert(binding: ViewBinding, data: T)
}