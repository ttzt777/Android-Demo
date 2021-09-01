package cc.bear3.player.ext.video.view

import android.content.Context
import android.util.AttributeSet
import cc.bear3.player.ext.video.controller.ListVideoPlayerController
import cc.bear3.player.kernal.proxy.DefaultVideoPlayerProxy
import cc.bear3.player.kernal.renderer.DefaultVideoRenderer
import cc.bear3.player.kernal.view.VideoPlayerView
import com.google.android.exoplayer2.Player

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
            DefaultVideoPlayerProxy(
                context,
                DefaultVideoRenderer(context),
                ListVideoPlayerController(context)
            )
        )

        playerProxy.player.repeatMode = Player.REPEAT_MODE_ONE
    }
}