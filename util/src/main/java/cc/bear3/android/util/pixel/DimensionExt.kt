package cc.bear3.android.util.pixel

import android.content.res.Resources
import android.util.TypedValue

/**
 *
 * @author TT
 * @since 2020-7-30
 */
fun Int.dp2px() : Float {
    return this.toFloat().dp2px()
}

fun Float.dp2px() : Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
}

fun Int.sp2px() : Float {
    return this.toFloat().sp2px()
}

fun Float.sp2px() : Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)
}