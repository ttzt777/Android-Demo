package cc.bear3.player.kernal.proxy

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.kernal.controller.IMediaPlayerController
import cc.bear3.player.kernal.controller.IVideoPlayerController
import cc.bear3.player.kernal.renderer.IVideoPlayerRenderer
import cc.bear3.player.kernal.state.PlayerState
import cc.bear3.player.kernal.view.VideoPlayerWrapper
import cc.bear3.player.kernal.protocol.IVideoEntity
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.video.VideoSize

/**
 *
 * @author TT
 * @since 2021-4-27
 */
class DefaultVideoPlayerProxy(
    context: Context,
    override val renderer: IVideoPlayerRenderer,
    presetController: IVideoPlayerController
) : DefaultMediaPlayerProxy(context),
    IVideoPlayerProxy, VideoPlayerWrapper.Callback {

    private val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    private val wrapper =
        VideoPlayerWrapper(
            context,
            callback = this
        )

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

    override fun prepareMediaSource(source: MediaSource, entity: IVideoEntity?) {
        prepareMediaSource(source)

        entity?.let {
            onVideoEntityPrepared(it)
        }
    }

    override fun onVideoEntityPrepared(entity: IVideoEntity) {
        controllers.forEach {
            (it as? IVideoPlayerController)?.onVideoEntityPrepared(entity)
        }
    }

    override fun play() {
        when (playerState) {
            PlayerState.Playing, PlayerState.Buffering -> return
            PlayerState.Paused, PlayerState.Confirm -> super.play()
//            PlayerState.Ended -> seekTo(0)
            else -> {
                prepare(mediaSource)
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

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        renderer.onVideoSizeChanged(videoSize)
    }

    override fun onPlayerWrapperSizeChanged(changed: Boolean) {
        if (changed) {
            with(wrapper) {
                renderer.onViewSizeChanged(width.toFloat(), height.toFloat())
            }
        }
    }
}