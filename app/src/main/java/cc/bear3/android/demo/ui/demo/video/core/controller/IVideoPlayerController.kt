package cc.bear3.android.demo.ui.demo.video.core.controller

/**
 *
 * @author TT
 * @since 2021-5-11
 */
interface IVideoPlayerController : IExoPlayerController {
    fun onEnterFullScreen()

    fun onExitFullScreen()
}