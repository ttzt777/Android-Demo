package cc.bear3.android.demo.ui.demo.video.core

import android.content.Context
import android.view.TextureView
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-4-27
 */
class DefaultVideoRendererController(context: Context) : IExoPlayerRendererController {
    override val textureView: TextureView = TextureView(context)

    override fun onVideoSizeChanged(
        width: Float,
        height: Float,
        unappliedRotationDegrees: Int
    ) {
        Timber.d("Video size change -- width($width), height($height), unappliedRotationDegress($unappliedRotationDegrees)")
    }
}