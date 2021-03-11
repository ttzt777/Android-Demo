package cc.bear3.android.demo.ui.media.pick.gallery

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author TT
 * @since 2021-3-4
 */
@Parcelize
data class MediaData(
    var id: Long,
    var name: String = "",
    var title: String = "",
    var uri: Uri? = null,
    var mimeType: String = "",
    var dateAdded: Long = 0,
    var dateModified: Long = 0,
    var width: Int = 0,
    var height: Int = 0,
    var size: Int = 0,
    var type: Type
) : Parcelable {
    var duration: Int = 0

    fun isGif() : Boolean {
        return "image/gif" == mimeType
    }

    fun isVideo() :Boolean {
        return type == Type.Video
    }

    enum class Type {
        Image,
        Video,
        Audio
    }
}