package cc.bear3.android.demo.ui.demo.video.player.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import cc.bear3.android.demo.ui.demo.video.player.core.controller.DefaultVideoPlayerController
import cc.bear3.android.demo.ui.demo.video.player.core.controller.IVideoPlayerController
import cc.bear3.android.demo.ui.demo.video.player.core.data.VideoEntity
import cc.bear3.android.demo.ui.demo.video.player.core.proxy.DefaultVideoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.player.core.proxy.IVideoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.player.core.renderer.DefaultVideoRenderer
import cc.bear3.android.demo.ui.demo.video.player.core.renderer.IVideoPlayerRenderer


/**
 *
 * @author TT
 * @since 2021-4-25
 */
open class VideoPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IVideoPlayerView {

    private lateinit var playerProxy: IVideoPlayerProxy

    override fun getVideoPlayerProxy(): IVideoPlayerProxy {
        makeSurePlayerProxy()
        return playerProxy
    }

    open fun updateData(entity: VideoEntity) {
        playerProxy.prepareVideo(entity)
    }

    fun setPlayerProxy(playerProxy: IVideoPlayerProxy) {
        if (this::playerProxy.isInitialized) {
            removeView(this.playerProxy.getWrapperView())
        }
        addView(playerProxy.getWrapperView(), createLayoutParams())

        this.playerProxy = playerProxy
    }

    fun setPlayerControllerAndRenderer(
        playerController: IVideoPlayerController = DefaultVideoPlayerController(context),
        playerRenderer: IVideoPlayerRenderer = DefaultVideoRenderer(context)
    ) {
        if (this::playerProxy.isInitialized) {
            removeView(this.playerProxy.getWrapperView())
        }

        createPlayerProxy(playerController, playerRenderer)
    }

    private fun makeSurePlayerProxy() {
        if (!this::playerProxy.isInitialized) {
            createPlayerProxy()
        }
    }

    private fun createPlayerProxy(
        controller: IVideoPlayerController = DefaultVideoPlayerController(context),
        renderer: IVideoPlayerRenderer = DefaultVideoRenderer(context)
    ) {
        playerProxy = DefaultVideoPlayerProxy(context, controller, renderer)
        addView(playerProxy.getWrapperView(), createLayoutParams())
    }

    private fun createLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}