package cc.bear3.player.autoplay.data

import androidx.recyclerview.widget.RecyclerView
import cc.bear3.player.core.proxy.IExoPlayerProxy

/**
 *
 * @author TT
 * @since 2021-5-20
 */
class AutoplayControllerParam {
    var playerProxy: IExoPlayerProxy? = null
    var recyclerView: RecyclerView? = null
    var videoPlayerId: Int = 0
}