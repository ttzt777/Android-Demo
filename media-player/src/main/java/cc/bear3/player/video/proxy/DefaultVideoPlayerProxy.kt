package cc.bear3.player.video.proxy

import android.content.Context
import cc.bear3.player.core.source.MediaSourceFactory
import cc.bear3.player.video.controller.IVideoPlayerController
import cc.bear3.player.video.renderer.IVideoPlayerRenderer
import cc.bear3.player.video.view.VideoPlayerWrapper
import com.google.android.exoplayer2.source.MediaSource

/**
 *
 * @author TT
 * @since 2021-4-27
 */
class DefaultVideoPlayerProxy(
    context: Context,
    renderer: IVideoPlayerRenderer,
    presetController: IVideoPlayerController
) : BaseVideoPlayerProxy(context, renderer, presetController),
    IVideoPlayerProxy, VideoPlayerWrapper.Callback {

    override fun createMediaSource(url: String): MediaSource {
        return MediaSourceFactory.createMediaSource(context, url)
    }
}