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
    Demo(R.string.menu_demo),
    View(R.string.menu_view),
    Media(R.string.menu_media),
    Util(R.string.menu_util),
    Jetpack(R.string.menu_jetpack),

    // 二级菜单 - System
    System_CrashHandler(R.string.menu_system_crashHandler),
    System_Bluetooth(R.string.menu_system_bluetooth),
    System_Socket(R.string.menu_system_socket),

    // 二级菜单 - Demo
    Demo_ChannelManager(R.string.menu_demo_channel_manager),

    // 二级菜单 - View
    View_Button(R.string.menu_view_button),
    View_TextView(R.string.menu_view_textView),
    View_EditView(R.string.menu_view_editView),
    View_SmartRefreshLayout(R.string.menu_view_smartRefreshLayout),
    View_SpannableTextView(R.string.menu_view_spannableTextView),
    View_CollapseTextView(R.string.menu_view_collapseTextView),
    View_RoundView(R.string.menu_view_roundView),

    // 二级菜单 - Media
    Media_Pick(R.string.menu_media_pick),

    // 二级菜单 - Util
    Util_SingleClick(R.string.menu_util_singleClick),
    Util_MultiLanguage(R.string.menu_util_multiLanguage),
    Util_Permission(R.string.menu_util_permission),

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
            System, Demo, View, Media, Util, Jetpack -> navController.navigate(this)

            Demo_ChannelManager -> navController.navigate(R.id.channelmanager_fragment)

            Media_Pick -> navController.navigate(R.id.mediapick_fragment)

            View_Button -> navController.navigate(R.id.button_fragment)
            View_TextView -> navController.navigate(R.id.textView_fragment)

            Util_Permission -> navController.navigate(R.id.permission_fragment)

            else -> {}
        }
    }

    fun createList() : List<ItemMenu>? {
        return when (this) {
            App -> listOf(System, Demo, View, Media, Util, Jetpack)

            System -> listOf(System_CrashHandler, System_Bluetooth, System_Socket)
            Demo -> listOf(Demo_ChannelManager)
            View -> listOf(View_TextView, View_EditView, View_SmartRefreshLayout, View_SpannableTextView, View_CollapseTextView, View_RoundView)
            Media -> listOf(Media_Pick)
            Util -> listOf(Util_SingleClick, Util_MultiLanguage, Util_Permission)
            Jetpack -> listOf(Jetpack_Room, Jetpack_DataStore, Jetpack_Navigation, Jetpack_Mvvm, Jetpack_WorkManager)
            else -> null
        }
    }

    private fun NavController.navigate(itemMenu: ItemMenu) {
        navigate(R.id.menu_list_fragment, MenuListFragment.newBundle(itemMenu))
    }
}