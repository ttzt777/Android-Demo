package cc.bear3.android.demo.ui.demo.video.player.auto.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import cc.bear3.android.demo.ui.demo.video.player.auto.data.AutoPlayControllerParam

/**
 *
 * @author TT
 * @since 2021-5-20
 */
abstract class AutoPlayController(
    lifecycle: Lifecycle,
    val controllerParam: AutoPlayControllerParam
) : LifecycleEventObserver {

    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> notifyStart()
            Lifecycle.Event.ON_PAUSE -> notifyStop()
            else -> {
            }
        }
    }

    open fun notifyStart() {

    }

    open fun notifyStop() {

    }
}