package cc.bear3.player.core.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.core.proxy.IMediaPlayerProxy
import cc.bear3.player.core.state.PlayerState
import cc.bear3.dispose.IDisposable

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IMediaPlayerController : IDisposable {
    var playerProxy: IMediaPlayerProxy

    fun getControllerView(inflater: LayoutInflater, container: ViewGroup?): View

    fun onPlayerStateChanged(playerState: PlayerState)

    fun onPlayerProgressChanged(positionMs: Long, totalMs: Long)

    fun onVolumeUp()

    fun onVolumeOff()
}