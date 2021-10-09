package cc.bear3.android.demo.ui.demo.channel

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.bear3.android.demo.databinding.ItemChannelManagerItemBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.adapter.fast.BindingViewHolder
import cc.bear3.adapter.kernel.AdapterStatus
import cc.bear3.util.utils.view.onClick
import cc.bear3.util.utils.view.visible

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
        BindingViewHolder<ItemChannelManagerItemBinding>(
            parent,
            ItemChannelManagerItemBinding::inflate
        ) {
        fun bindView(
            data: ChannelData,
            type: ChannelManagerType,
            inEditMode: Boolean,
            listener: IChannelOperation
        ) {
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