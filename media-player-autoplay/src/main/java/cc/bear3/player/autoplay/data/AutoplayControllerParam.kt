package cc.bear3.player.autoplay.data

import androidx.recyclerview.widget.RecyclerView
import cc.bear3.player.kernal.proxy.IMediaPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
class AutoplayControllerParam {
    var playerProxy: IMediaPlayerProxy? = null
    var recyclerView: RecyclerView? = null
    var videoPlayerId: Int = 0
}