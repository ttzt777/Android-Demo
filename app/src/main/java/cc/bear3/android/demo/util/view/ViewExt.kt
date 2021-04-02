package cc.bear3.android.demo.util.view

import android.view.View

/**
 *
 * @author TT
 * @since 2021-3-22
 */
fun View.visible(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}