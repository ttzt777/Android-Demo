package cc.bear3.android.demo.ui.demo.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemChannelManagerItemBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.android.demo.ui.util.ext.onClick
import cc.bear3.android.demo.util.view.visible
import cc.bear3.util.statusadapter.AdapterStatus

/**
 *
 * @author TT
 * @since 2021-3-17
 */
class ChannelManagerItemAdapter(private val listener: IChannelOperation) :
    BaseAdapter<ChannelData, ChannelManagerItemAdapter.ChannelManagerItemViewHolder>() {
    private var inEditMode = false
    private var channelManagerType = ChannelManagerType.Suggest

    init {
        status = AdapterStatus.Null
    }

    override fun onBindCustomViewHolder(holder: ChannelManagerItemViewHolder, position: Int) {
        holder.bindView(getData(position), channelManagerType, inEditMode, listener)
    }

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ChannelManagerItemViewHolder {
        return ChannelManagerItemViewHolder(parent)
    }

    fun updateData(targetList: List<ChannelData>?, type: ChannelManagerType, inEditMode: Boolean) {
        this.inEditMode = inEditMode
        this.channelManagerType = type
        super.dataRefresh(targetList, false)
    }

    class ChannelManagerItemViewHolder(parent: ViewGroup) :
        BaseAdapter.ContentViewHolder(parent, R.layout.item_channel_manager_item) {
        private val binding = ItemChannelManagerItemBinding.bind(itemView)

        fun bindView(data: ChannelData, type: ChannelManagerType, inEditMode: Boolean, listener: IChannelOperation) {
            binding.name.text = data.name

            binding.delete.visible(type == ChannelManagerType.Subscribed && inEditMode)

            binding.name.onClick {
                when (type) {
                    ChannelManagerType.Suggest, ChannelManagerType.Deleted -> {
                        listener.onSubscribeChannelAdd(data, type)
                    }
                    ChannelManagerType.Subscribed -> {
                        if (!inEditMode) {
                            listener.onSubscribeChannelClick(data)
                        }
                    }
                }
            }
            binding.delete.onClick {
                listener.onSubscribeChannelRemove(data)
            }
        }
    }
}