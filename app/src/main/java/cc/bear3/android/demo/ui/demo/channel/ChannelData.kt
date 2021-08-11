package cc.bear3.android.demo.ui.demo.channel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @author TT
 * @since 2021-3-11
 */
@Parcelize
data class ChannelData(
    val id: Long,
    val name: String
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        val newData = other as? ChannelData ?: return false

        if (this.id != newData.id) {
            return false
        }

        if (this.name != newData.name) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = id.toInt()
        result = 17 * result + name.hashCode()
        return result
    }
}