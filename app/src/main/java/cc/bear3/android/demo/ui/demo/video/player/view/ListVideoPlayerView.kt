package cc.bear3.android.demo.ui.demo.video.player.view

import android.content.Context
import android.util.AttributeSet
import cc.bear3.player.video.controller.ListVideoPlayerController
import cc.bear3.player.video.proxy.ListVideoPlayerProxy
import cc.bear3.player.video.renderer.DefaultVideoRenderer

/**
 *
 * @author TT
 * @since 2021-6-1
 */
class ListVideoPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : VideoPlayerView(context, attrs, defStyleAttr) {
    init {
        setPlayerProxy(
            ListVideoPlayerProxy(
                context,
                DefaultVideoRenderer(context),
                ListVideoPlayerController(context)
            )
        )
    }
}