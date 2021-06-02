package cc.bear3.android.demo.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 *
 * @author TT
 * @since 2021-6-2
 */
abstract class SingleFastAdapter<T, VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    BaseAdapter<T, BindingViewHolder<VB>>() {
    final override fun onBindCustomViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        convert(holder.binding, getData(position))
    }

    final override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<VB> {
        return BindingViewHolder(parent, inflate)
    }

    abstract fun convert(binding: VB, data: T)
}