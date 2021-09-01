package cc.bear3.player.autoplay.processor

import cc.bear3.player.autoplay.data.AutoplayControllerParam
import cc.bear3.player.kernal.protocol.IMediaPlayerDisposable
import cc.bear3.player.kernal.proxy.IMediaPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
interface IAutoplayProcessor : IMediaPlayerDisposable {
    val controllerParam: AutoplayControllerParam
    var crtPlayerProxy: IMediaPlayerProxy?

    fun start()

    fun pause()

    fun stop()
}