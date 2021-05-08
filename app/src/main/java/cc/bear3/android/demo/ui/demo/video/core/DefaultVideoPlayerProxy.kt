package cc.bear3.android.demo.ui.demo.video.core

import android.content.Context
import android.view.View
import com.google.android.exoplayer2.video.VideoListener

/**
 *
 * @author TT
 * @since 2021-4-27
 */
class DefaultVideoPlayerProxy(
    context: Context,
    val rendererController: IExoPlayerRendererController
) : DefaultExoPlayerProxy(context), VideoListener {

    init {
        player.addVideoListener(this)
        player.setVideoTextureView(rendererController.textureView)
    }

//    override fun getTextureView(): TextureView {
//        return rendererController.textureView
//    }
//
//    override fun getControllerView(): View {
//        return viewController!!.getControllerView(LayoutInflater.from(context), null)
//    }

    override fun dispose() {
        super.dispose()
        player.removeVideoListener(this)
    }

    override fun onVideoSizeChanged(
        width: Int,
        height: Int,
        unappliedRotationDegrees: Int,
        pixelWidthHeightRatio: Float
    ) {
        super.onVideoSizeChanged(width, height, unappliedRotationDegrees, pixelWidthHeightRatio)
        rendererController.onVideoSizeChanged(
            width * pixelWidthHeightRatio,
            height * pixelWidthHeightRatio,
            unappliedRotationDegrees
        )
    }
}