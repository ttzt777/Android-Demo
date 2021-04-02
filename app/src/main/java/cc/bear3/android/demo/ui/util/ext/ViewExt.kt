package cc.bear3.android.demo.ui.util.ext

import android.view.View

/**
 *
 * @author TT
 * @since 2020-12-22
 */

private const val MIN_CLICK_DELAY_TIME = 500L
private var lastClickTime = 0L

inline fun View?.onClick(crossinline block: () -> Unit) {
    if (this == null) {
        return
    }

    this.setOnClickListener() {
        if (!isAvailableClick()) {
            return@setOnClickListener
        }

        block()
    }
}

fun View?.removeOnClick() {
    if (this == null) {
        return
    }

    setOnClickListener(null)
    isClickable = false
}

fun isAvailableClick(): Boolean {
    val crtTime = System.currentTimeMillis()
    val delta = crtTime - lastClickTime

    return if (delta in 0 until MIN_CLICK_DELAY_TIME) {
        false
    } else {
        lastClickTime = crtTime
        true
    }
}