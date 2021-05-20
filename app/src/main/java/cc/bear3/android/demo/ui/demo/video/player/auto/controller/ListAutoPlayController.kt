package cc.bear3.android.demo.ui.demo.video.player.auto.controller

import androidx.lifecycle.Lifecycle
import cc.bear3.android.demo.ui.demo.video.player.auto.data.AutoPlayControllerParam

/**
 *
 * @author TT
 * @since 2021-5-20
 */
class ListAutoPlayController(lifecycle: Lifecycle, controllerParam: AutoPlayControllerParam) :
    AutoPlayController(lifecycle, controllerParam) {

    override fun notifyStart() {
        super.notifyStart()
    }

    override fun notifyStop() {
        super.notifyStop()
    }
}