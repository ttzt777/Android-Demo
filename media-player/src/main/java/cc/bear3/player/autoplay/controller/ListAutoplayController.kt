package cc.bear3.player.autoplay.controller

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import cc.bear3.player.BuildConfig
import cc.bear3.player.autoplay.data.AutoplayControllerParam
import cc.bear3.player.autoplay.processor.IListAutoplayProcessor
import cc.bear3.player.autoplay.processor.ListAutoplayProcessor

/**
 *
 * @author TT
 * @since 2021-5-20
 */
class ListAutoplayController(lifecycle: Lifecycle, controllerParam: AutoplayControllerParam) :
    AutoplayController(lifecycle, controllerParam) {

    private val scrollListener: RecyclerView.OnScrollListener

    init {
        if (BuildConfig.DEBUG && controllerParam.recyclerView == null) {
            throw IllegalArgumentException("When you use ListAutoplayController, make sure your recyclerview is not null")
        }

        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                this@ListAutoplayController.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                this@ListAutoplayController.onScrollStateChanged(recyclerView, newState)
            }
        }

        controllerParam.recyclerView?.addOnScrollListener(scrollListener)
        processor = ListAutoplayProcessor(controllerParam)
    }

    override fun dispose() {
        super.dispose()
        controllerParam.recyclerView?.removeOnScrollListener(scrollListener)
    }

    /**
     * 处理两种情况
     * 1. 滑动中，当前播放的播放器被滑出屏幕，停止当前的播放器
     */
    private fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            processor.start()
        }
    }

    /**
     * 2. 滑动完成后，处理当前第一个满足条件的播放器进行播放
     */
    private fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        (processor as? IListAutoplayProcessor)?.pauseByScroll()
    }
}