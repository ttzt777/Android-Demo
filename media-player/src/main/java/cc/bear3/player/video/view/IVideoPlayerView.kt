package cc.bear3.player.video.view

import cc.bear3.player.video.proxy.IVideoPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
interface IVideoPlayerView {
    fun getVideoPlayerProxy(): IVideoPlayerProxy
}