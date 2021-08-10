package cc.bear3.player.core.controller

import cc.bear3.player.core.data.IVideoProtocol

/**
 *
 * @author TT
 * @since 2021-5-11
 */
interface IVideoPlayerController : IExoPlayerController {
    fun onVideoEntityPrepared(entity: IVideoProtocol)
}