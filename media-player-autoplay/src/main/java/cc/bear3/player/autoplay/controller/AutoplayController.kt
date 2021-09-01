package cc.bear3.player.autoplay.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import cc.bear3.player.autoplay.data.AutoplayControllerParam
import cc.bear3.player.autoplay.processor.AutoplayProcessor
import cc.bear3.player.autoplay.processor.IAutoplayProcessor
import cc.bear3.player.kernal.protocol.IMediaPlayerDisposable

/**
 *
 * @author TT
 * @since 2021-5-20
 */
open class AutoplayController(
    lifecycle: Lifecycle,
    val controllerParam: AutoplayControllerParam
) : LifecycleEventObserver, IMediaPlayerDisposable {

    protected var processor: IAutoplayProcessor = AutoplayProcessor(controllerParam)

    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> notifyStart()
            Lifecycle.Event.ON_PAUSE -> notifyStop()
            Lifecycle.Event.ON_DESTROY -> dispose()
            else -> {
            }
        }
    }

    open fun notifyStart() {
        processor.start()
    }

    open fun notifyStop() {
        processor.pause()
    }

    override fun dispose() {
        processor.dispose()
    }
}