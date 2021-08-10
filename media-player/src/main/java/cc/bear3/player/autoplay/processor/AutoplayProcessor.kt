package cc.bear3.android.demo.ui.demo.video.player.autoplay.processor

import cc.bear3.android.demo.ui.demo.video.player.autoplay.data.AutoplayControllerParam
import cc.bear3.player.core.proxy.IExoPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
open class AutoplayProcessor(final override val controllerParam: AutoplayControllerParam) :
    IAutoplayProcessor {
    override var crtPlayerProxy: IExoPlayerProxy? = controllerParam.playerProxy

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