package cc.bear3.android.demo.ui.demo.video.player.view

import cc.bear3.android.demo.ui.demo.video.player.core.proxy.IVideoPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
interface IVideoPlayerView {
    fun getVideoPlayerProxy(): IVideoPlayerProxy
}