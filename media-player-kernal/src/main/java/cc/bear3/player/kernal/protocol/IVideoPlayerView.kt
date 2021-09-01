package cc.bear3.player.kernal.protocol

import cc.bear3.player.kernal.proxy.IVideoPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
interface IVideoPlayerView {
    fun getVideoPlayerProxy(): IVideoPlayerProxy
}