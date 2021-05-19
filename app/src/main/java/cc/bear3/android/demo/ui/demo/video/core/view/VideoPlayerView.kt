package cc.bear3.android.demo.ui.demo.video.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import cc.bear3.android.demo.BuildConfig
import cc.bear3.android.demo.ui.demo.video.core.VideoEntity
import cc.bear3.android.demo.ui.demo.video.core.controller.DefaultVideoPlayerController
import cc.bear3.android.demo.ui.demo.video.core.proxy.DefaultVideoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.core.proxy.IVideoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.core.renderer.DefaultVideoRenderer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


/**
 *
 * @author TT
 * @since 2021-4-25
 */
class VideoPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val playerProxy: IVideoPlayerProxy

    init {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        playerProxy = DefaultVideoPlayerProxy(
            context,
            DefaultVideoPlayerController(
                context
            ),
            DefaultVideoRenderer(
                context
            )
        )
        addView(playerProxy.getWrapperView(), layoutParams)
    }

    open fun updateData(entity: VideoEntity) {
        playerProxy.prepareVideo(entity)
    }
}