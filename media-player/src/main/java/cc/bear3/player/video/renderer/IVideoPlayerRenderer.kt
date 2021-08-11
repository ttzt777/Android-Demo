package cc.bear3.player.video.renderer

import android.view.TextureView

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IVideoPlayerRenderer {
    val textureView: TextureView
    var viewWidth: Float
    var viewHeight: Float
    var videoWidth: Float
    var videoHeight: Float

    fun onViewSizeChanged(
        width: Float,
        height: Float
    )

    fun onVideoSizeChanged(
        width: Float,
        height: Float,
        unappliedRotationDegrees: Int
    )
}