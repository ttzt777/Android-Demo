package cc.bear3.player.kernal.controller

import cc.bear3.player.kernal.protocol.IVideoEntity

/**
 *
 * @author TT
 * @since 2021-5-11
 */
interface IVideoPlayerController : IMediaPlayerController {
    fun onVideoEntityPrepared(entity: IVideoEntity)
}