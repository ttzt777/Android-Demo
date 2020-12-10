package cc.bear3.android.demo.data

import androidx.annotation.StringRes
import androidx.navigation.NavController
import androidx.navigation.Navigation
import cc.bear3.android.demo.R
import cc.bear3.android.demo.app.App
import cc.bear3.android.demo.ui.common.MenuListFragment

/**
 *
 * @author TT
 * @since 2020-12-4
 */
enum class ItemMenu(@StringRes val stringId: Int) {
    App(R.string.app_name),
    System(R.string.menu_system),
    View(R.string.menu_view),
    Bitmap(R.string.menu_bitmap),
    Util(R.string.menu_util),

    ViewTextView(R.string.menu_view_textView);

    fun click(navController: NavController) {
        when(this) {
            View -> navController.navigate(R.id.menu_list_fragment, MenuListFragment.newBundle(View))
            ViewTextView -> navController.navigate(R.id.textView_fragment)
            else -> {}
        }
    }

    fun createList() : List<ItemMenu>? {
        return when (this) {
            App -> listOf(System, View, Bitmap, Util)
            View -> listOf(ViewTextView)
            else -> null
        }
    }
}