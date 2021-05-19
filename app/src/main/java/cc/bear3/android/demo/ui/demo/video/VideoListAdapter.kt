package cc.bear3.android.demo.ui.demo.video

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemVideoListBinding
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.android.demo.ui.demo.video.core.VideoEntity

/**
 *
 * @author TT
 * @since 2021-5-19
 */
class VideoListAdapter : BaseAdapter<VideoEntity, VideoListAdapter.VideoViewHolder>() {

    class VideoViewHolder(parent: ViewGroup) : BaseAdapter.ContentViewHolder(parent, R.layout.item_video_list) {
        val binding = ItemVideoListBinding.bind(itemView)

        fun bindView(entity: VideoEntity) {
            binding.playerView.updateData(entity)
        }
    }

    override fun onBindCustomViewHolder(holder: VideoViewHolder, position: Int) {
         holder.bindView(getData(position))
    }

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): VideoViewHolder {
        return VideoViewHolder(parent)
    }
}