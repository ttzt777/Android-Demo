package cc.bear3.android.demo.data

import androidx.annotation.StringRes
import androidx.navigation.NavController
import cc.bear3.android.demo.R
import cc.bear3.android.demo.ui.common.MenuListFragment

/**
 *
 * @author TT
 * @since 2020-12-4
 */
@Suppress("EnumEntryName")
enum class ItemMenu(@StringRes val stringId: Int) {
    // app级
    App(R.string.app_name),

    // 一级主菜单
    System(R.string.menu_system),
    View(R.string.menu_view),
    Bitmap(R.string.menu_bitmap),
    Util(R.string.menu_util),
    Jetpack(R.string.menu_jetpack),

    // 二级菜单 - System
    System_CrashHandler(R.string.menu_system_crashHandler),
    System_Bluetooth(R.string.menu_system_bluetooth),
    System_Socket(R.string.menu_system_socket),

    // 二级菜单 - View
    View_TextView(R.string.menu_view_textView),
    View_SmartRefreshLayout(R.string.menu_view_smartRefreshLayout),
    View_SpannableTextView(R.string.menu_view_spannableTextView),
    View_CollapseTextView(R.string.menu_view_collapseTextView),
    View_RoundView(R.string.menu_view_roundView),

    // 二级菜单 - Bitmap

    // 二级菜单 - Util
    Util_SingleClick(R.string.menu_util_singleClick),
    Util_MultiLanguage(R.string.menu_util_multiLanguage),

    // 二级菜单 - Jetpack
    Jetpack_Room(R.string.menu_jetpack_room),
    Jetpack_DataStore(R.string.menu_jetpack_dataStore),
    Jetpack_Navigation(R.string.menu_jetpack_navigation),
    Jetpack_Mvvm(R.string.menu_jetpack_mvvm),
    Jetpack_WorkManager(R.string.menu_jetpack_workManager),

    // 占位用，免得每次都去改最后的分号
    Null(-1);

    fun click(navController: NavController) {
        when(this) {
            System, View, Bitmap, Util, Jetpack -> navController.navigate(this)
            View_TextView -> navController.navigate(R.id.textView_fragment)
            else -> {}
        }
    }

    fun createList() : List<ItemMenu>? {
        return when (this) {
            App -> listOf(System, View, Bitmap, Util, Jetpack)

            System -> listOf(System_CrashHandler, System_Bluetooth, System_Socket)
            View -> listOf(View_TextView, View_SmartRefreshLayout, View_SpannableTextView, View_CollapseTextView, View_RoundView)
            Bitmap -> listOf()
            Util -> listOf(Util_SingleClick, Util_MultiLanguage)
            Jetpack -> listOf(Jetpack_Room, Jetpack_DataStore, Jetpack_Navigation, Jetpack_Mvvm, Jetpack_WorkManager)
            else -> null
        }
    }

    private fun NavController.navigate(itemMenu: ItemMenu) {
        navigate(R.id.menu_list_fragment, MenuListFragment.newBundle(itemMenu))
    }
}