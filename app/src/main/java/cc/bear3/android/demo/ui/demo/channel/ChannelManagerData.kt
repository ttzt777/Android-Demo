package cc.bear3.android.demo.ui.demo.channel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author TT
 * @since 2021-3-17
 */
@Parcelize
data class ChannelManagerData(
    val title: CharSequence,
    val channelList: MutableList<ChannelData>,
    val type: ChannelManagerType = ChannelManagerType.Suggest
) : Parcelable

enum class ChannelManagerType {
    Subscribed,
    Deleted,
    Suggest
}