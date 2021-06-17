package cc.bear3.android.demo.ui.demo.video.player.core.proxy

import android.content.Context
import cc.bear3.android.demo.ui.demo.video.player.core.controller.IVideoPlayerController
import cc.bear3.android.demo.ui.demo.video.player.core.renderer.IVideoPlayerRenderer
import cc.bear3.android.demo.ui.demo.video.player.core.source.MediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource

/**
 *
 * @author TT
 * @since 2021-6-1
 */
class ListVideoPlayerProxy (
    context: Context,
    controller: IVideoPlayerController,
    renderer: IVideoPlayerRenderer
) : DefaultVideoPlayerProxy(context, controller, renderer) {
    init {
        volumeOff()
    }

    override fun createMediaSource(url: String): MediaSource {
        return MediaSourceFactory.createLoopMediaSource(url)
    }
}