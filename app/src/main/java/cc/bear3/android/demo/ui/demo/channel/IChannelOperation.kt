package cc.bear3.android.demo.ui.demo.channel

/**
 *
 * @author TT
 * @since 2021-3-22
 */
interface IChannelOperation {
    fun onEditModeChange(targetEditMode: Boolean)

    fun onSubscribeChannelClick(channelData: ChannelData)

    fun onSubscribeChannelAdd(channelData: ChannelData, type: ChannelManagerType)

    fun onSubscribeChannelRemove(channelData: ChannelData)

    fun onSubscribeChannelMove(channelDataList: List<ChannelData>)
}