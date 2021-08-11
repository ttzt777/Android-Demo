package cc.bear3.android.demo.ui.demo.channel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @author TT
 * @since 2021-3-17
 */
@Parcelize
data class ChannelManagerData(
    val title: CharSequence,
    val type: ChannelManagerType = ChannelManagerType.Suggest,
    val channelList: MutableList<ChannelData>
) : Parcelable {
    companion object {
        fun newInstance(
            type: ChannelManagerType,
            channelList: MutableList<ChannelData>
        ): ChannelManagerData {
            val title = when (type) {
                ChannelManagerType.Subscribed -> "我订阅的频道"
                ChannelManagerType.Deleted -> "最近删除的频道"
                ChannelManagerType.Suggest -> "推荐的频道"
            }
            return ChannelManagerData(title, type, channelList)
        }
    }
}

enum class ChannelManagerType {
    Subscribed,
    Deleted,
    Suggest
}