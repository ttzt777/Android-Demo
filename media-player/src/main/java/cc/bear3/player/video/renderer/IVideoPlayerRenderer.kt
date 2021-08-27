package cc.bear3.player.video.renderer

import android.view.TextureView
import com.google.android.exoplayer2.video.VideoSize

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

    fun onVideoSizeChanged(videoSize: VideoSize)
}