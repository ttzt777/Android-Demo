package cc.bear3.android.demo.ui.demo.video.player.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View

/**
 *
 * @author TT
 * @since 2021-5-11
 */
class VideoControllerEventView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var callback: Callback? = null

    init {
        isFocusableInTouchMode = true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            requestFocus()
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        callback?.let {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (it.onBackClick()){
                        return true
                    }
                }
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    it.onVolumeUpClick()
                    return true
                }
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    it.onVolumeDownClick()
                    return true
                }
            }
        }

        return super.onKeyUp(keyCode, event)
    }

    interface Callback {
        fun onBackClick(): Boolean

        fun onVolumeUpClick()

        fun onVolumeDownClick()
    }
}