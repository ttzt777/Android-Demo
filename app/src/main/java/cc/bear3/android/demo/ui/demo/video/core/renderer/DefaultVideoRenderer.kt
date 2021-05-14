package cc.bear3.android.demo.ui.demo.video.core.renderer

import android.content.Context
import android.view.Gravity
import android.view.TextureView
import android.widget.FrameLayout
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-4-27
 */
open class DefaultVideoRenderer(context: Context) :
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

    override fun onVideoSizeChanged(
        width: Float,
        height: Float,
        unappliedRotationDegrees: Int
    ) {
        Timber.d("Video size change -- width($width), height($height), unappliedRotationDegress($unappliedRotationDegrees)")
        this.videoWidth = width
        this.videoHeight = height

        updateTextureViewSize()
    }

    protected open fun updateTextureViewSize() {
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

    protected open fun updateTextureViewLayoutParams(targetWidth: Int, targetHeight: Int) {
        Timber.d("Update textureView layout params -- width($targetWidth), height($targetHeight)")

        textureView.layoutParams = (textureView.layoutParams as FrameLayout.LayoutParams).apply {
            width = targetWidth
            height = targetHeight
            gravity = Gravity.CENTER
        }
    }

    protected fun isParamsInvalid(vararg params: Float): Boolean {
        for (param in params) {
            if (param == 0f) {
                return true
            }
        }

        return false
    }
}