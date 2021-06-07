package cc.bear3.util.utils.system

import android.content.res.Resources

/**
 *
 * @author TT
 * @since 2021-3-1
 */
object SystemManager {
    var statusBarHeight = 24f.dp2px().toInt()

    val windowWidth = Resources.getSystem().displayMetrics.widthPixels

    val windowHeight = Resources.getSystem().displayMetrics.heightPixels
}