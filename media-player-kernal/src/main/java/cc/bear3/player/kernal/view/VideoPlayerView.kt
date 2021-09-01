package cc.bear3.player.kernal.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import cc.bear3.player.kernal.protocol.IVideoPlayerView
import cc.bear3.player.kernal.controller.IVideoPlayerController
import cc.bear3.player.kernal.proxy.IVideoPlayerProxy
import cc.bear3.player.kernal.renderer.IVideoPlayerRenderer
import cc.bear3.player.kernal.source.MediaSourceFactory
import cc.bear3.player.kernal.controller.DefaultVideoPlayerController
import cc.bear3.player.kernal.protocol.IVideoEntity
import cc.bear3.player.kernal.proxy.DefaultVideoPlayerProxy
import cc.bear3.player.kernal.renderer.DefaultVideoRenderer

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

    protected lateinit var playerProxy: IVideoPlayerProxy
        private set

    override fun getVideoPlayerProxy(): IVideoPlayerProxy {
        makeSurePlayerProxy()
        return playerProxy
    }

    open fun updateData(entity: IVideoEntity) {
        makeSurePlayerProxy()

        playerProxy.prepareMediaSource(
            MediaSourceFactory.createMediaSource(
                context,
                entity.videoUrl
            ),
            entity
        )
    }

    fun setPlayerProxy(playerProxy: IVideoPlayerProxy) {
        if (this::playerProxy.isInitialized) {
            if (this.playerProxy == playerProxy) {
                return
            }

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