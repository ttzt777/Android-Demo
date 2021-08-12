package cc.bear3.player.video.proxy

import android.view.View
import androidx.lifecycle.MutableLiveData
import cc.bear3.player.video.renderer.IVideoPlayerRenderer
import cc.bear3.player.video.data.IVideoProtocol
import cc.bear3.player.core.proxy.IMediaPlayerProxy

/**
 *
 * @author TT
 * @since 2021-4-27
 */
interface IVideoPlayerProxy :
    IMediaPlayerProxy {
    val renderer: IVideoPlayerRenderer

    fun getWrapperView(): View

    fun prepareVideo(entity: IVideoProtocol)
}