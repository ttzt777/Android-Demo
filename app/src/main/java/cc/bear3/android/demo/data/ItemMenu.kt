package cc.bear3.android.demo.data

import android.content.Context
import androidx.annotation.StringRes
import cc.bear3.android.demo.R
import cc.bear3.android.demo.ui.common.MenuListPage
import cc.bear3.android.demo.ui.demo.channel.ChannelDemoPage
import cc.bear3.android.demo.ui.demo.video.VideoDemoPage
import cc.bear3.android.demo.ui.media.pick.MediaPickPage
import cc.bear3.android.demo.ui.util.permission.PermissionPage
import cc.bear3.android.demo.ui.view.button.ButtonPage
import cc.bear3.android.demo.ui.view.custom.CustomViewPage
import cc.bear3.android.demo.ui.view.imageView.ImageViewPage
import cc.bear3.android.demo.ui.view.textView.TextViewPage

/**
 *
 * @author TT
 * @since 2020-12-4
 */
@Suppress("EnumEntryName", "unused")
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
    Demo_Video(R.string.menu_demo_video),

    // 二级菜单 - View
    View_Button(R.string.menu_view_button),
    View_TextView(R.string.menu_view_textView),
    View_ImageView(R.string.menu_view_imageView),
    View_EditView(R.string.menu_view_editView),
    View_CustomView(R.string.menu_view_customView),
    View_SmartRefreshLayout(R.string.menu_view_smartRefreshLayout),
    View_SpannableTextView(R.string.menu_view_spannableTextView),
    View_CollapseTextView(R.string.menu_view_collapseTextView),

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

    fun click(context: Context) {
        when (this) {
            System, Demo, View, Media, Util, Jetpack -> MenuListPage.invoke(context, this)

            Demo_ChannelManager -> ChannelDemoPage.invoke(context)
            Demo_Video -> VideoDemoPage.invoke(context)

            Media_Pick -> MediaPickPage.invoke(context)

            View_Button -> ButtonPage.invoke(context)
            View_TextView -> TextViewPage.invoke(context)
            View_ImageView -> ImageViewPage.invoke(context)
            View_CustomView -> CustomViewPage.invoke(context)

            Util_Permission -> PermissionPage.invoke(context)

            else -> {
            }
        }
    }

    fun createList(): List<ItemMenu>? {
        return when (this) {
            App -> listOf(System, Demo, View, Media, Util, Jetpack)

            System -> listOf(System_CrashHandler, System_Bluetooth, System_Socket)
            Demo -> listOf(Demo_ChannelManager, Demo_Video)
            View -> listOf(
                View_Button,
                View_TextView,
                View_ImageView,
                View_EditView,
                View_CustomView,
                View_SmartRefreshLayout,
                View_SpannableTextView,
                View_CollapseTextView
            )
            Media -> listOf(Media_Pick)
            Util -> listOf(Util_SingleClick, Util_MultiLanguage, Util_Permission)
            Jetpack -> listOf(
                Jetpack_Room,
                Jetpack_DataStore,
                Jetpack_Navigation,
                Jetpack_Mvvm,
                Jetpack_WorkManager
            )
            else -> null
        }
    }
}