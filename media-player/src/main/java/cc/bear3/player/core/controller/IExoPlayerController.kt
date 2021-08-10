package cc.bear3.player.core.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.core.proxy.IExoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.player.core.state.PlayerState
import cc.bear3.dispose.IDisposable

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IExoPlayerController : IDisposable {
    var playerProxy: IExoPlayerProxy

    fun getControllerView(inflater: LayoutInflater, container: ViewGroup?): View

    fun onPlayerStateChanged(playerState: PlayerState)

    fun onPlayerProgressChanged(positionMs: Long, totalMs: Long)

    fun onVolumeUp()

    fun onVolumeOff()
}