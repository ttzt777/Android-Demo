package cc.bear3.android.demo.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.data.ItemMenu
import cc.bear3.android.demo.databinding.ItemMenuListBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.adapter.kernel.AContentViewHolder
import cc.bear3.adapter.kernel.inflate

/**
 *
 * @author TT
 * @since 2020-12-7
 */
class MenuListAdapter : BaseAdapter<ItemMenu, MenuListAdapter.MenuViewHolder>() {

    override fun onBindCustomViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindView(getData(position))
    }

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MenuViewHolder {
        return MenuViewHolder(parent.inflate(R.layout.item_menu_list))
    }

    class MenuViewHolder(view: View) : AContentViewHolder(view) {
        private val binding = ItemMenuListBinding.bind(view)

        fun bindView(data: ItemMenu) {
            with(binding.title) {
                setText(data.stringId)
                setOnClickListener() {
                    data.click(context)
                }
            }
        }
    }
}