package cc.bear3.android.demo.ui.demo.video

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemVideoListBinding
import cc.bear3.android.demo.databinding.ItemVideoListImageBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.android.demo.ui.demo.video.player.core.data.VideoEntity
import cc.bear3.android.demo.ui.util.ext.onClick
import com.bumptech.glide.Glide

/**
 *
 * @author TT
 * @since 2021-5-19
 */
class VideoListAdapter : BaseAdapter<VideoEntity, BaseAdapter.ContentViewHolder>() {

    class VideoViewHolder(parent: ViewGroup) :
        BaseAdapter.ContentViewHolder(parent, R.layout.item_video_list) {
        val binding = ItemVideoListBinding.bind(itemView)

        fun bindView(entity: VideoEntity) {
            binding.playerView.updateData(entity)

            binding.playerView.onClick {
                VideoDetailPage.invoke(binding.playerView.context, entity)
            }
        }
    }

    class ImageViewHolder(parent: ViewGroup) :
        BaseAdapter.ContentViewHolder(parent, R.layout.item_video_list_image) {
        val binding = ItemVideoListImageBinding.bind(itemView)

        fun bindView(entity: VideoEntity) {
            Glide.with(itemView.context).load(entity.url).into(binding.videoCover)
        }
    }

    override fun onBindCustomViewHolder(holder: ContentViewHolder, position: Int) {
        val entity = getData(position)
        if (holder is VideoViewHolder) {
            holder.bindView(entity)
        } else if (holder is ImageViewHolder) {
            holder.bindView(entity)
        }
    }

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ContentViewHolder {
//        return if (viewType == 2) {
//            VideoViewHolder(parent)
//        } else {
//            ImageViewHolder(parent)
//        }
        return VideoViewHolder(parent)
    }

    override fun getCustomViewType(position: Int): Int {
        return position
    }
}