package cc.bear3.android.demo.ui.demo.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemChannelManagerBinding
import cc.bear3.android.demo.ui.base.BaseAdapter


/**
 *
 * @author TT
 * @since 2021-3-15
 */
class ChannelManagerAdapter : BaseAdapter<ChannelManagerData, ChannelManagerAdapter.ChannelManagerViewHolder>(){

    override fun onBindCustomViewHolder(holder: ChannelManagerViewHolder, position: Int) {
        holder.bindView(getData(position))
    }

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ChannelManagerViewHolder {
        return ChannelManagerViewHolder(parent)
    }

    class ChannelManagerViewHolder(parent: ViewGroup) : BaseAdapter.ContentViewHolder(parent, R.layout.item_channel_manager) {
        private val binding = ItemChannelManagerBinding.bind(itemView)
        private val adapter by lazy {
            ChannelManagerItemAdapter()
        }

        init {
            with(binding.list) {
                layoutManager = GridLayoutManager(context, 4)
                adapter = this@ChannelManagerViewHolder.adapter
            }
        }

        fun bindView(data: ChannelManagerData) {
            binding.title.text = data.title
            adapter.dataRefresh(data.channelList)
        }
    }
}
