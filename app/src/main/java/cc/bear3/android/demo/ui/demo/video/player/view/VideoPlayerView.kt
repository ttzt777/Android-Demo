package cc.bear3.android.demo.ui.demo.video.player.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import cc.bear3.android.demo.ui.demo.video.player.VideoEntity
import cc.bear3.player.video.controller.DefaultVideoPlayerController
import cc.bear3.player.video.controller.IVideoPlayerController
import cc.bear3.player.video.proxy.DefaultVideoPlayerProxy
import cc.bear3.player.video.proxy.IVideoPlayerProxy
import cc.bear3.player.video.renderer.DefaultVideoRenderer
import cc.bear3.player.video.renderer.IVideoPlayerRenderer
import cc.bear3.player.video.view.IVideoPlayerView


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
        playerRenderer: IVideoPlayerRenderer = DefaultVideoRenderer(context),
        playerController: IVideoPlayerController = DefaultVideoPlayerController(context)
    ) {
        if (this::playerProxy.isInitialized) {
            removeView(this.playerProxy.getWrapperView())
        }

        createPlayerProxy(playerRenderer, playerController)
    }

    private fun makeSurePlayerProxy() {
        if (!this::playerProxy.isInitialized) {
            createPlayerProxy()
        }
    }

    private fun createPlayerProxy(
        renderer: IVideoPlayerRenderer = DefaultVideoRenderer(context),
        controller: IVideoPlayerController = DefaultVideoPlayerController(context)
    ) {
        playerProxy = DefaultVideoPlayerProxy(context, renderer, controller)
        addView(playerProxy.getWrapperView(), createLayoutParams())
    }

    private fun createLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}