package cc.bear3.android.demo.ui.demo.video.core.proxy

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.BuildConfig
import cc.bear3.android.demo.ui.demo.video.core.PlayerState
import cc.bear3.android.demo.ui.demo.video.core.VideoEntity
import cc.bear3.android.demo.ui.demo.video.core.controller.IExoPlayerController
import cc.bear3.android.demo.ui.demo.video.core.renderer.IVideoPlayerRenderer
import cc.bear3.android.demo.ui.demo.video.core.view.VideoPlayerWrapper
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
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
    controller: IExoPlayerController,
    final override val renderer: IVideoPlayerRenderer
) : DefaultExoPlayerProxy(context, controller),
    IVideoPlayerProxy, VideoListener, VideoPlayerWrapper.Callback {

    protected val wrapper = VideoPlayerWrapper(context, callback = this)
    protected var videoEntity : VideoEntity? = null

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

//    override fun getTextureView(): TextureView {
//        return renderer.textureView
//    }

    override fun getWrapperView(): View {
        return wrapper
    }

    override fun play() {
        when (playerState) {
            PlayerState.Playing, PlayerState.Buffering -> return
            PlayerState.Paused, PlayerState.Confirm -> super.play()
            else -> {
                val entity = videoEntity ?: return
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, BuildConfig.APPLICATION_ID)
                )
                val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(entity.url))
                prepare(videoSource)
                super.play()
            }
        }
    }

    override fun prepareVideo(entity: VideoEntity) {
        this.videoEntity = entity
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

    private fun getControllerView(): View {
        return controller!!.getControllerView(LayoutInflater.from(context), null)
    }
}