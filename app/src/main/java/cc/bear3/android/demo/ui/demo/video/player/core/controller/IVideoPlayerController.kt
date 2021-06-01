package cc.bear3.android.demo.ui.demo.video.player.core.controller

import cc.bear3.android.demo.ui.demo.video.player.core.data.VideoEntity

/**
 *
 * @author TT
 * @since 2021-5-11
 */
interface IVideoPlayerController : IExoPlayerController {
    fun onVideoEntityPrepared(entity: VideoEntity)
}