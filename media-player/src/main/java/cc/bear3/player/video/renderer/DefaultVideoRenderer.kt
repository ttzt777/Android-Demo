package cc.bear3.player.video.renderer

import android.content.Context
import android.view.Gravity
import android.view.TextureView
import android.widget.FrameLayout
import com.google.android.exoplayer2.video.VideoSize
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-4-27
 */
class DefaultVideoRenderer(context: Context) :
    IVideoPlayerRenderer {
    override val textureView: TextureView = TextureView(context)

    override var viewWidth: Float = 0f
    override var viewHeight: Float = 0f
    override var videoWidth: Float = 0f
    override var videoHeight: Float = 0f

    override fun onViewSizeChanged(
        width: Float,
        height: Float
    ) {
        Timber.d("View size change -- width($width), height($height)")

        if (this.viewWidth == width && this.viewHeight == height) {
            return
        }

        this.viewWidth = width
        this.viewHeight = height

        updateTextureViewSize()
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        Timber.d("Video size change -- width($videoSize.width), height($videoSize.height), unappliedRotationDegress($videoSize.unappliedRotationDegrees)")
        this.videoWidth = videoSize.width.toFloat()
        this.videoHeight = videoSize.height.toFloat()

        updateTextureViewSize()
    }

    private fun updateTextureViewSize() {
        if (isParamsInvalid(viewWidth, viewHeight, videoWidth, videoHeight)) {
            return
        }

        val viewRatio = viewWidth / viewHeight
        val videoRatio = videoWidth / videoHeight

        Timber.d("Update textureView size -- view ration($viewRatio), video ratio($videoRatio)")

        val targetTextureViewWidth: Float
        val targetTextureViewHeight: Float

        if (videoRatio >= viewRatio) {
            // 视频宽高比>=控件宽高比  宽度充满，上下留白
            targetTextureViewWidth = viewWidth
            targetTextureViewHeight = targetTextureViewWidth / videoRatio
        } else {
            // 视频宽高比<控件宽高比  高度充满，左右留白
            targetTextureViewHeight = viewHeight
            targetTextureViewWidth = targetTextureViewHeight * videoRatio
        }

        updateTextureViewLayoutParams(
            targetTextureViewWidth.toInt(),
            targetTextureViewHeight.toInt()
        )
    }

    private fun updateTextureViewLayoutParams(targetWidth: Int, targetHeight: Int) {
        Timber.d("Update textureView layout params -- width($targetWidth), height($targetHeight)")

        textureView.layoutParams = (textureView.layoutParams as FrameLayout.LayoutParams).apply {
            width = targetWidth
            height = targetHeight
            gravity = Gravity.CENTER
        }
    }

    private fun isParamsInvalid(vararg params: Float): Boolean {
        for (param in params) {
            if (param == 0f) {
                return true
            }
        }

        return false
    }
}