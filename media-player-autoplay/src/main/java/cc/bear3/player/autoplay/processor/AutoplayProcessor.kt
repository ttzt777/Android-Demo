package cc.bear3.player.autoplay.processor

import cc.bear3.player.autoplay.data.AutoplayControllerParam
import cc.bear3.player.kernal.proxy.IMediaPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
open class AutoplayProcessor(final override val controllerParam: AutoplayControllerParam) :
    IAutoplayProcessor {
    override var crtPlayerProxy: IMediaPlayerProxy? = controllerParam.playerProxy

    override fun start() {
        crtPlayerProxy?.play()
    }

    override fun pause() {
        crtPlayerProxy?.pause()
    }

    override fun stop() {
        crtPlayerProxy?.stop()
    }

    override fun dispose() {
        crtPlayerProxy?.stop()
        crtPlayerProxy = null
    }
}