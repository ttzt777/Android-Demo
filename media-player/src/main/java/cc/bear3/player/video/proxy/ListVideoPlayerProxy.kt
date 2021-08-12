package cc.bear3.player.video.proxy

import android.content.Context
import cc.bear3.player.video.controller.IVideoPlayerController
import cc.bear3.player.video.renderer.IVideoPlayerRenderer
import cc.bear3.player.core.source.MediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource

/**
 *
 * @author TT
 * @since 2021-6-1
 */
class ListVideoPlayerProxy (
    context: Context,
    renderer: IVideoPlayerRenderer,
    presetController: IVideoPlayerController
) : DefaultVideoPlayerProxy(context, renderer, presetController) {
    init {
        volumeOff()
    }

    override fun createMediaSource(url: String): MediaSource {
        return MediaSourceFactory.createLoopMediaSource(context, url)
    }
}