package cc.bear3.android.demo.data

import androidx.annotation.StringRes
import cc.bear3.android.demo.R

/**
 *
 * @author TT
 * @since 2020-12-4
 */
enum class ItemMenu(@StringRes val stringId: Int) {
    System(R.string.menu_system),
    View(R.string.menu_view),
    Bitmap(R.string.menu_bitmap),
    Util(R.string.menu_util);
}

fun ItemMenu.click() {
//    when (this) {
//        ItemMenu.System ->
//    }
}