package cc.bear3.android.demo.ui.demo.video.core.proxy

import android.view.View
import cc.bear3.android.demo.ui.demo.video.core.VideoEntity
import cc.bear3.android.demo.ui.demo.video.core.renderer.IVideoPlayerRenderer

/**
 *
 * @author TT
 * @since 2021-4-27
 */
interface IVideoPlayerProxy :
    IExoPlayerProxy {
    val renderer: IVideoPlayerRenderer

    fun getWrapperView(): View

    fun prepareVideo(entity: VideoEntity)
}