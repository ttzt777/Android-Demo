package cc.bear3.android.demo.ui.media.pick.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemMediaGalleryImageBinding
import cc.bear3.android.demo.databinding.ItemMediaGalleryVideoBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.android.demo.util.date.DateUtil
import com.bumptech.glide.Glide

/**
 *
 * @author TT
 * @since 2021-3-8
 */
class MediaGalleryAdapter : BaseAdapter<MediaData, MediaGalleryAdapter.MediaHolder>() {

    override fun onBindCustomViewHolder(holder: MediaHolder, position: Int) {
        holder.bindView(getData(position))
    }

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MediaHolder {
        return when (viewType) {
            TYPE_AUDIO -> AudioHolder(parent)
            TYPE_VIDEO -> VideoHolder(parent)
            else -> ImageHolder(parent)
        }
    }

    abstract class MediaHolder(parent: ViewGroup, resId: Int) :
        BaseAdapter.ContentViewHolder(parent, resId) {
        abstract fun bindView(data: MediaData)
    }

    override fun getCustomViewType(position: Int): Int {
        val data = getData(position)
        return when (data.type) {
            MediaData.Type.Audio -> TYPE_AUDIO
            MediaData.Type.Video -> TYPE_VIDEO
            else -> TYPE_IMAGE
        }
    }

    class ImageHolder(parent: ViewGroup) : MediaHolder(parent, R.layout.item_media_gallery_image) {
        private val binding = ItemMediaGalleryImageBinding.bind(itemView)

        override fun bindView(data: MediaData) {
            with(binding) {
                Glide.with(itemView).load(data.uri).into(cover)

                tag.visibility = if (data.isGif()) View.VISIBLE else View.GONE
            }
        }
    }

    class VideoHolder(parent: ViewGroup) : MediaHolder(parent, R.layout.item_media_gallery_video) {
        private val binding = ItemMediaGalleryVideoBinding.bind(itemView)

        override fun bindView(data: MediaData) {
            with(binding) {
                Glide.with(itemView).load(data.uri).into(cover)

                if (data.duration > 0) {
                    time.text = DateUtil.formatTime(data.duration.toLong())
                    time.visibility = View.VISIBLE
                } else {
                    time.visibility = View.GONE
                }
            }
        }
    }

    class AudioHolder(parent: ViewGroup) : MediaHolder(parent, R.layout.item_media_gallery_audio) {
        private val binding = ItemMediaGalleryVideoBinding.bind(itemView)

        override fun bindView(data: MediaData) {
            with(binding) {
                if (data.duration > 0) {
                    time.text = DateUtil.formatTime(data.duration.toLong())
                    time.visibility = View.VISIBLE
                } else {
                    time.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
        private const val TYPE_AUDIO = 2
    }
}