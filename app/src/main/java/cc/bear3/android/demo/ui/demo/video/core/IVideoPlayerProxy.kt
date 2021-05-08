package cc.bear3.android.demo.ui.demo.video.core

import android.view.TextureView
import android.view.View

/**
 *
 * @author TT
 * @since 2021-4-27
 */
interface IVideoPlayerProxy : IExoPlayerProxy {
    fun getTextureView(): TextureView

    fun getControllerView() : View
}