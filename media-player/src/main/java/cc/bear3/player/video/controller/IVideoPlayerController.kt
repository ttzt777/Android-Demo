package cc.bear3.player.video.controller

import cc.bear3.player.core.controller.IMediaPlayerController
import cc.bear3.player.video.data.IVideoProtocol

/**
 *
 * @author TT
 * @since 2021-5-11
 */
interface IVideoPlayerController : IMediaPlayerController {
    fun onVideoEntityPrepared(entity: IVideoProtocol)
}