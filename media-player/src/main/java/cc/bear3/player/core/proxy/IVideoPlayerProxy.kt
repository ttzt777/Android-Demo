package cc.bear3.player.core.proxy

import android.view.View
import cc.bear3.player.core.renderer.IVideoPlayerRenderer
import cc.bear3.player.core.data.IVideoProtocol

/**
 *
 * @author TT
 * @since 2021-4-27
 */
interface IVideoPlayerProxy :
    IExoPlayerProxy {
    val renderer: IVideoPlayerRenderer

    fun getWrapperView(): View

    fun prepareVideo(entity: IVideoProtocol)
}