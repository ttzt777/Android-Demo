package cc.bear3.android.demo.ui.demo.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.PageVideoDetailBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.demo.video.player.autoplay.builder.AutoPlayerControllerBuilder
import cc.bear3.android.demo.ui.demo.video.player.autoplay.controller.AutoplayController
import cc.bear3.android.demo.ui.demo.video.player.core.data.VideoEntity
import cc.bear3.android.demo.util.context.startWithAnim
import com.gyf.immersionbar.ImmersionBar

/**
 *
 * @author TT
 * @since 2021-5-31
 */
class VideoDetailPage : BaseActivity<PageVideoDetailBinding>(PageVideoDetailBinding::inflate) {
    override fun initView(savedInstanceState: Bundle?) {
        AutoPlayerControllerBuilder.newBuilder(AutoplayController::class.java)
            .setPlayerProxy(binding.playerView.getVideoPlayerProxy())
            .monitor(lifecycle)

        val entity = intent.getParcelableExtra<VideoEntity>(ARG_VIDEO_ENTITY) ?: return
        with(binding.playerView) {
            updateData(entity)
            getVideoPlayerProxy().play()
        }
    }

    override fun setImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.transparent)
            .statusBarDarkFont(false)
            .init()
    }

    companion object {
        private const val ARG_VIDEO_ENTITY = "arg_video_entity"

        fun invoke(context: Context, entity: VideoEntity) {
            val intent = Intent(context, VideoDetailPage::class.java).apply {
                putExtra(ARG_VIDEO_ENTITY, entity)
            }
            context.startWithAnim(intent)
        }
    }
}