package cc.bear3.android.demo.ui.media.pick.gallery

import android.view.View
import androidx.viewbinding.ViewBinding
import cc.bear3.android.demo.databinding.ItemMediaGalleryAudioBinding
import cc.bear3.android.demo.databinding.ItemMediaGalleryImageBinding
import cc.bear3.android.demo.databinding.ItemMediaGalleryVideoBinding
import cc.bear3.android.demo.ui.base.MultiFastAdapter
import cc.bear3.util.utils.date.DateUtil
import com.bumptech.glide.Glide

/**
 *
 * @author TT
 * @since 2021-3-8
 */
class MediaGalleryAdapter : MultiFastAdapter<MediaData>() {

    override fun convert(binding: ViewBinding, data: MediaData) {
        if (binding is ItemMediaGalleryImageBinding) {
            binding.convert(data)
        } else if (binding is ItemMediaGalleryVideoBinding) {
            binding.convert(data)
        } else if (binding is ItemMediaGalleryAudioBinding) {
            binding.convert(data)
        }
    }

    private fun ItemMediaGalleryImageBinding.convert(data: MediaData) {
        Glide.with(cover).load(data.uri).into(cover)

        tag.visibility = if (data.isGif()) View.VISIBLE else View.GONE
    }

    private fun ItemMediaGalleryVideoBinding.convert(data: MediaData) {
        Glide.with(cover).load(data.uri).into(cover)

        if (data.duration > 0) {
            time.text = DateUtil.formatMediaTime(data.duration.toLong())
            time.visibility = View.VISIBLE
        } else {
            time.visibility = View.GONE
        }
    }

    private fun ItemMediaGalleryAudioBinding.convert(data: MediaData) {
        if (data.duration > 0) {
            time.text = DateUtil.formatMediaTime(data.duration.toLong())
            time.visibility = View.VISIBLE
        } else {
            time.visibility = View.GONE
        }
    }
}