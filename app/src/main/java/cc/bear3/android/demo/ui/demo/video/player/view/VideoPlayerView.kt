package cc.bear3.android.demo.ui.demo.video.player.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import cc.bear3.android.demo.ui.demo.video.player.core.controller.DefaultVideoPlayerController
import cc.bear3.android.demo.ui.demo.video.player.core.data.VideoEntity
import cc.bear3.android.demo.ui.demo.video.player.core.proxy.DefaultVideoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.player.core.proxy.IVideoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.player.core.renderer.DefaultVideoRenderer


/**
 *
 * @author TT
 * @since 2021-4-25
 */
class VideoPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IVideoPlayerView {

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

    override fun getVideoPlayerProxy(): IVideoPlayerProxy {
        return playerProxy
    }

    open fun updateData(entity: VideoEntity) {
        playerProxy.prepareVideo(entity)
    }
}