package cc.bear3.player.autoplay.processor

import cc.bear3.dispose.IDisposable
import cc.bear3.player.autoplay.data.AutoplayControllerParam
import cc.bear3.player.core.proxy.IMediaPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
interface IAutoplayProcessor : IDisposable {
    val controllerParam: AutoplayControllerParam
    var crtPlayerProxy: IMediaPlayerProxy?

    fun start()

    fun pause()

    fun stop()
}