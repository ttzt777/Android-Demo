package cc.bear3.android.demo.ui.demo.channel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author TT
 * @since 2021-3-11
 */
@Parcelize
data class ChannelData(
    val id: Long,
    val name: String
) : Parcelable