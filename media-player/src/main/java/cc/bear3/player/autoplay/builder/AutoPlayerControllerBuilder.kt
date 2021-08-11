package cc.bear3.player.autoplay.builder

import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import cc.bear3.player.autoplay.controller.AutoplayController
import cc.bear3.player.autoplay.data.AutoplayControllerParam
import cc.bear3.player.core.proxy.IExoPlayerProxy
import timber.log.Timber
import java.lang.Exception

/**
 *
 * @author TT
 * @since 2021-5-21
 */
class AutoPlayerControllerBuilder<T : AutoplayController> private constructor() {
    private val controllerParam = AutoplayControllerParam()
    private lateinit var clazz: Class<T>

    fun setPlayerProxy(playerProxy: IExoPlayerProxy): AutoPlayerControllerBuilder<T> {
        controllerParam.playerProxy = playerProxy
        return this
    }

    fun setRecyclerView(recyclerView: RecyclerView): AutoPlayerControllerBuilder<T> {
        controllerParam.recyclerView = recyclerView
        return this
    }

    fun setVideoPlayerId(@IdRes videoPlayerId: Int): AutoPlayerControllerBuilder<T> {
        controllerParam.videoPlayerId = videoPlayerId
        return this
    }

    fun monitor(lifecycle: Lifecycle) {
        try {
            val constructor = clazz.getConstructor(Lifecycle::class.java, AutoplayControllerParam::class.java)
            constructor.newInstance(lifecycle, controllerParam)
        } catch (e: Exception) {
            Timber.e("Auto play controller builder monitor error -- $e")
        }
    }

    companion object {
        fun <T : AutoplayController> newBuilder(clazz: Class<T>): AutoPlayerControllerBuilder<T> {
            val builder = AutoPlayerControllerBuilder<T>()
            builder.clazz = clazz
            return builder
        }
    }
}
