package cc.bear3.player.kernal.proxy

import android.view.View
import cc.bear3.player.kernal.renderer.IVideoPlayerRenderer
import cc.bear3.player.kernal.protocol.IVideoEntity
import com.google.android.exoplayer2.source.MediaSource

/**
 *
 * @author TT
 * @since 2021-4-27
 */
interface IVideoPlayerProxy :
    IMediaPlayerProxy {
    val renderer: IVideoPlayerRenderer

    fun getWrapperView(): View

    fun prepareMediaSource(source: MediaSource, entity: IVideoEntity?)

    fun onVideoEntityPrepared(entity: IVideoEntity)
}