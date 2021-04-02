package cc.bear3.android.demo.ui.demo.channel

import android.app.Activity
import android.content.Context
import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemChannelManagerOtherBinding
import cc.bear3.android.demo.databinding.ItemChannelManagerSubscribedBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.android.demo.ui.util.ext.onClick
import cc.bear3.android.demo.ui.util.ext.removeOnClick
import cc.bear3.android.demo.util.view.visible
import timber.log.Timber
import java.util.*


/**
 *
 * @author TT
 * @since 2021-3-15
 */
private const val KEY_SUBSCRIBED = 0
private const val KEY_DELETED = 1
private const val KEY_SUGGEST_INDEX = 2

private const val TYPE_SUBSCRIBED = 0
private const val TYPE_DELETED = 1

class ChannelManagerAdapter(private val context: Context) :
    RecyclerView.Adapter<ChannelManagerAdapter.ChannelManagerViewHolder>() {
    private val dataList = SparseArray<ChannelManagerData>()
    private var suggestIndex = KEY_SUGGEST_INDEX

    private var inEditMode = false
    private val listener = object : IChannelOperation {
        override fun onEditModeChange(targetEditMode: Boolean) {
            inEditMode = targetEditMode
            notifyDataSetChanged()
        }

        override fun onSubscribeChannelClick(channelData: ChannelData) {
            ChannelManager.currentChannel.value = channelData
            (context as? Activity)?.onBackPressed()
        }

        override fun onSubscribeChannelAdd(channelData: ChannelData, type: ChannelManagerType) {
            var subscribedData = dataList.get(KEY_SUBSCRIBED)
            if (subscribedData == null) {
                subscribedData = ChannelManagerData.newInstance(
                    ChannelManagerType.Subscribed,
                    mutableListOf(channelData)
                )
                dataList.put(KEY_SUBSCRIBED, subscribedData)
            } else {
                subscribedData.channelList.add(channelData)
            }

            ChannelManager.channelList.value = subscribedData.channelList

            if (type == ChannelManagerType.Deleted) {
                // 从最近删除添加的
                val deletedData = dataList.get(KEY_DELETED)
                if (deletedData != null) {
                    deletedData.channelList.remove(channelData)
                    if (deletedData.channelList.isNullOrEmpty()) {
                        dataList.remove(KEY_DELETED)
                    }
                }
            } else {
                // 从推荐添加的，推荐可能存在多个
                val size = dataList.size()
                for (index in 0 until size) {
                    val key = dataList.keyAt(index)
                    if (key < KEY_SUGGEST_INDEX) {
                        continue
                    }

                    val data = dataList.get(key)
                    if (data.channelList.remove(channelData)) {
                        if (data.channelList.isNullOrEmpty()) {
                            dataList.remove(key)
                            break
                        }
                    }
                }
            }

            onEditModeChange(true)
        }

        override fun onSubscribeChannelRemove(channelData: ChannelData) {
            dataList.get(KEY_SUBSCRIBED)?.channelList?.remove(channelData)

            ChannelManager.channelList.value =
                dataList.get(KEY_SUBSCRIBED)?.channelList ?: mutableListOf()

            val deletedData = dataList.get(KEY_DELETED)
            if (deletedData == null) {
                dataList.put(
                    KEY_DELETED,
                    ChannelManagerData.newInstance(
                        ChannelManagerType.Deleted,
                        mutableListOf(channelData)
                    )
                )
            } else {
                deletedData.channelList.add(channelData)
            }

            notifyDataSetChanged()
        }

        override fun onSubscribeChannelMove(channelDataList: List<ChannelData>) {
            dataList.get(KEY_SUBSCRIBED)?.let {
                it.channelList.clear()
                it.channelList.addAll(channelDataList.toMutableList())

                ChannelManager.channelList.value = it.channelList
                onEditModeChange(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelManagerViewHolder {
        return when (viewType) {
            TYPE_SUBSCRIBED -> ChannelManagerSubscribedHolder(parent, listener)
            else -> ChannelManagerOtherHolder(parent, listener)
        }
    }

    override fun onBindViewHolder(holder: ChannelManagerViewHolder, position: Int) {
        holder.bindView(getData(position), inEditMode)
    }

    override fun getItemCount(): Int {
        return dataList.size()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getData(position).type == ChannelManagerType.Subscribed) {
            TYPE_SUBSCRIBED
        } else {
            TYPE_DELETED
        }
    }

    fun updateData(data: ChannelManagerData, reset: Boolean = true) {
        if (reset) {
            dataList.clear()
            suggestIndex = KEY_SUGGEST_INDEX
        }

        when (data.type) {
            ChannelManagerType.Subscribed -> dataList.put(KEY_SUBSCRIBED, data)
            ChannelManagerType.Deleted -> dataList.put(KEY_DELETED, data)
            else -> dataList.put(suggestIndex++, data)
        }

        notifyDataSetChanged()
    }

    fun updateData(targetList: List<ChannelManagerData>, reset: Boolean = true) {
        if (reset) {
            dataList.clear()
            suggestIndex = KEY_SUGGEST_INDEX
        }

        for (data in targetList) {
            when (data.type) {
                ChannelManagerType.Subscribed -> dataList.put(KEY_SUBSCRIBED, data)
                ChannelManagerType.Deleted -> dataList.put(KEY_DELETED, data)
                else -> dataList.put(suggestIndex++, data)
            }
        }

        notifyDataSetChanged()
    }

    protected fun getData(position: Int): ChannelManagerData {
        return dataList.get(dataList.keyAt(position))
    }

//    private fun changeMode(inEditMode: Boolean) {
//        this.inEditMode = inEditMode
//        notifyDataSetChanged()
//    }

    class ChannelManagerSubscribedHolder(
        parent: ViewGroup,
        private val listener: IChannelOperation
    ) :
        ChannelManagerViewHolder(parent, R.layout.item_channel_manager_subscribed) {
        private val binding = ItemChannelManagerSubscribedBinding.bind(itemView)
        private val adapter = ChannelManagerItemAdapter(listener)

        private val touchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val swipeFlag = 0
                val dragFlag =
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlag, swipeFlag);
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val originPos = viewHolder.absoluteAdapterPosition
                val targetPos = target.absoluteAdapterPosition
                adapter.notifyItemMoved(originPos, targetPos)

                with(adapter.dataList) {
                    val originData = removeAt(originPos)
                    add(targetPos, originData)
                }

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                Timber.d("onSelectedChanged - $actionState")
                if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
                    listener.onSubscribeChannelMove(adapter.dataList)
                }
            }
        }
        private val touchHelper = ItemTouchHelper(touchHelperCallback)

        init {
            with(binding.list) {
                layoutManager = GridLayoutManager(context, 4)
                adapter = this@ChannelManagerSubscribedHolder.adapter

                touchHelper.attachToRecyclerView(this)
            }
        }

        override fun bindView(data: ChannelManagerData, inEditMode: Boolean) {
            binding.title.text = data.title

            binding.edit.text = if (inEditMode) {
                "完成"
            } else {
                "编辑"
            }
            binding.edit.onClick {
                listener.onEditModeChange(!inEditMode)
            }

            adapter.updateData(data.channelList, data.type, inEditMode)
        }
    }

    class ChannelManagerOtherHolder(
        parent: ViewGroup,
        listener: IChannelOperation
    ) :
        ChannelManagerViewHolder(parent, R.layout.item_channel_manager_other) {
        private val binding = ItemChannelManagerOtherBinding.bind(itemView)
        private val adapter = ChannelManagerItemAdapter(listener)

        init {
            with(binding.list) {
                layoutManager = GridLayoutManager(context, 4)
                adapter = this@ChannelManagerOtherHolder.adapter
            }
        }

        override fun bindView(data: ChannelManagerData, inEditMode: Boolean) {
            binding.title.text = data.title

            adapter.updateData(data.channelList, data.type, inEditMode)
        }
    }

    abstract class ChannelManagerViewHolder(parent: ViewGroup, resId: Int) :
        BaseAdapter.ContentViewHolder(parent, resId) {
        abstract fun bindView(data: ChannelManagerData, inEditMode: Boolean)
    }
}
