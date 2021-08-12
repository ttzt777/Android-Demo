package cc.bear3.player.video.proxy

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.core.controller.IMediaPlayerController
import cc.bear3.player.core.manager.PlayerProtocolManager
import cc.bear3.player.core.proxy.DefaultMediaPlayerProxy
import cc.bear3.player.core.source.MediaSourceFactory
import cc.bear3.player.core.state.PlayerState
import cc.bear3.player.video.controller.IVideoPlayerController
import cc.bear3.player.video.data.IVideoProtocol
import cc.bear3.player.video.renderer.IVideoPlayerRenderer
import cc.bear3.player.video.view.VideoPlayerWrapper
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.exoplayer2.video.VideoListener
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-4-27
 */
@Suppress("MemberVisibilityCanBePrivate")
open class DefaultVideoPlayerProxy(
    context: Context,
    final override val renderer: IVideoPlayerRenderer,
    presetController: IVideoPlayerController
) : DefaultMediaPlayerProxy(context),
    IVideoPlayerProxy, VideoPlayerWrapper.Callback {

    protected val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    protected val wrapper =
        VideoPlayerWrapper(
            context,
            callback = this
        )

    protected var videoEntity: IVideoProtocol? = null

    init {
        wrapper.setBackgroundColor(Color.BLACK)
        wrapper.addView(renderer.textureView, layoutParams)
        addController(presetController)

        player.addListener(this)
        player.setVideoTextureView(renderer.textureView)
    }

    override fun getWrapperView(): View {
        return wrapper
    }

    override fun play() {
        when (playerState) {
            PlayerState.Playing, PlayerState.Buffering -> return
            PlayerState.Paused, PlayerState.Confirm -> super.play()
//            PlayerState.Ended -> seekTo(0)
            else -> {
                val entity = videoEntity ?: return
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, PlayerProtocolManager.protocol.getApplicationId())
                )
                prepare(createMediaSource(entity.url))
                super.play()
            }
        }
    }

    override fun pause() {
        super.pause()
        if (playerState == PlayerState.Buffering) {
            changePlayerState(PlayerState.Paused)
        }
    }

    override fun prepareVideo(entity: IVideoProtocol) {
        this.videoEntity = entity
        controllers.forEach {
            (it as? IVideoPlayerController)?.onVideoEntityPrepared(entity)
        }
        changePlayerState(PlayerState.Idle)
    }

    override fun dispose() {
        super.dispose()
        player.removeVideoListener(this)
    }

    override fun addController(controller: IMediaPlayerController) {
        super.addController(controller)

        val view = controller.getControllerView(LayoutInflater.from(context), null)
        wrapper.addView(view)
    }

    override fun removeController(controller: IMediaPlayerController) {
        super.removeController(controller)

        val view = controller.getControllerView(LayoutInflater.from(context), null)
        wrapper.removeView(view)
    }

    override fun onVideoSizeChanged(
        width: Int,
        height: Int,
        unappliedRotationDegrees: Int,
        pixelWidthHeightRatio: Float
    ) {
        Timber.d("Video size change -- width($width), height($height), unappliedRotationDegress($unappliedRotationDegrees), pixelWidthHeightRatio($pixelWidthHeightRatio)")

        renderer.onVideoSizeChanged(
            width.toFloat(),
            height.toFloat(),
            unappliedRotationDegrees
        )
    }

    override fun onPlayerWrapperSizeChanged(changed: Boolean) {
        if (changed) {
            with(wrapper) {
                renderer.onViewSizeChanged(width.toFloat(), height.toFloat())
            }
        }
    }

    protected open fun createMediaSource(url: String): MediaSource {
        return MediaSourceFactory.createMediaSource(context, url)
    }
}