package cc.bear3.android.demo.ui.demo.video.player.autoplay.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import cc.bear3.android.demo.manager.IDisposable
import cc.bear3.android.demo.ui.demo.video.player.autoplay.data.AutoplayControllerParam
import cc.bear3.android.demo.ui.demo.video.player.autoplay.processor.DefaultAutoplayProcessor
import cc.bear3.android.demo.ui.demo.video.player.autoplay.processor.IAutoplayProcessor

/**
 *
 * @author TT
 * @since 2021-5-20
 */
abstract class AutoplayController(
    lifecycle: Lifecycle,
    val controllerParam: AutoplayControllerParam
) : LifecycleEventObserver, IDisposable {

    protected var processor : IAutoplayProcessor = DefaultAutoplayProcessor(controllerParam)

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