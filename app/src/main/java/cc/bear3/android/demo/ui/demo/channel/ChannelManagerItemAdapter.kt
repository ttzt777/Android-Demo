package cc.bear3.android.demo.ui.demo.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemChannelManagerItemBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.util.statusadapter.AdapterStatus

/**
 *
 * @author TT
 * @since 2021-3-17
 */
class ChannelManagerItemAdapter : BaseAdapter<ChannelData, ChannelManagerItemAdapter.ChannelManagerItemViewHolder>() {
    init {
        status = AdapterStatus.Null
    }

    override fun onBindCustomViewHolder(holder: ChannelManagerItemViewHolder, position: Int) {
        holder.bindView(getData(position))
    }

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ChannelManagerItemViewHolder {
        return ChannelManagerItemViewHolder(parent)
    }

    class ChannelManagerItemViewHolder(parent: ViewGroup) : BaseAdapter.ContentViewHolder(parent, R.layout.item_channel_manager_item) {
        private val binding = ItemChannelManagerItemBinding.bind(itemView)

        fun bindView(data: ChannelData) {
            binding.name.text = data.name
        }
    }
}