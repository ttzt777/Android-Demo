package cc.bear3.player.core.proxy

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.PlayerProtocolManager
import cc.bear3.player.core.controller.IVideoPlayerController
import cc.bear3.player.core.data.IVideoProtocol
import cc.bear3.player.core.renderer.IVideoPlayerRenderer
import cc.bear3.player.core.source.MediaSourceFactory
import cc.bear3.player.core.state.PlayerState
import cc.bear3.player.core.view.VideoPlayerWrapper
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
open class DefaultVideoPlayerProxy(
    context: Context,
    controller: IVideoPlayerController,
    final override val renderer: IVideoPlayerRenderer
) : DefaultExoPlayerProxy(context, controller),
    IVideoPlayerProxy, VideoListener, VideoPlayerWrapper.Callback {

    protected val wrapper =
        VideoPlayerWrapper(
            context,
            callback = this
        )
    protected var videoEntity: IVideoProtocol? = null

    init {
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        wrapper.setBackgroundColor(Color.BLACK)
        wrapper.addView(renderer.textureView, layoutParams)
        wrapper.addView(getControllerView(), layoutParams)

        player.addVideoListener(this)
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
        (controller as? IVideoPlayerController)?.onVideoEntityPrepared(entity)
        changePlayerState(PlayerState.Idle)
    }

    override fun dispose() {
        super.dispose()
        player.removeVideoListener(this)
    }

    override fun onVideoSizeChanged(
        width: Int,
        height: Int,
        unappliedRotationDegrees: Int,
        pixelWidthHeightRatio: Float
    ) {
        super.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio)

        Timber.d("Video size change -- width($width), height($height), unappliedRotationDegress($unappliedRotationDegrees), pixelWidthHeightRatio($pixelWidthHeightRatio)")

        renderer.onVideoSizeChanged(
            width * pixelWidthHeightRatio,
            height * pixelWidthHeightRatio,
            unappliedRotationDegrees
        )

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

    private fun getControllerView(): View {
        return controller!!.getControllerView(LayoutInflater.from(context), null)
    }
}