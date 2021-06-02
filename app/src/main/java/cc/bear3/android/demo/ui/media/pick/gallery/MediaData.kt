package cc.bear3.android.demo.ui.media.pick.gallery

import android.net.Uri
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import cc.bear3.android.demo.databinding.ItemMediaGalleryAudioBinding
import cc.bear3.android.demo.databinding.ItemMediaGalleryImageBinding
import cc.bear3.android.demo.databinding.ItemMediaGalleryVideoBinding
import cc.bear3.android.demo.ui.base.IMultiData
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
) : Parcelable, IMultiData {
    var duration: Int = 0

    override fun getViewBindingFun(): (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding {
        return when (type) {
            Type.Image -> ItemMediaGalleryImageBinding::inflate
            Type.Video -> ItemMediaGalleryVideoBinding::inflate
            Type.Audio -> ItemMediaGalleryAudioBinding::inflate
        }
    }

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