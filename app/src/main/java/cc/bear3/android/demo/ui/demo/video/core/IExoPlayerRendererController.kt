package cc.bear3.android.demo.ui.demo.video.core

import android.view.TextureView

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IExoPlayerRendererController {
    val textureView: TextureView

    fun onVideoSizeChanged(
        width: Float,
        height: Float,
        unappliedRotationDegrees: Int
    )
}