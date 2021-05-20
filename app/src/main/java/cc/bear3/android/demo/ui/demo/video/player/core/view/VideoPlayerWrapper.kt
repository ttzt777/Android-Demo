package cc.bear3.android.demo.ui.demo.video.player.core.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 *
 * @author TT
 * @since 2021-5-11
 */
class VideoPlayerWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val callback: Callback? = null
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
//        Timber.d("OnLayout - changed($changed), left($left), top($top), right($right), bottom($bottom)")

        callback?.onPlayerWrapperSizeChanged(changed)
    }

    interface Callback {
        fun onPlayerWrapperSizeChanged(changed: Boolean)
    }
}