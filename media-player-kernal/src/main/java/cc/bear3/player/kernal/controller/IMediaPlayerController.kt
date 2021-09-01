package cc.bear3.player.kernal.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.kernal.protocol.IMediaPlayerDisposable
import cc.bear3.player.kernal.proxy.IMediaPlayerProxy
import cc.bear3.player.kernal.state.PlayerState

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IMediaPlayerController : IMediaPlayerDisposable {
    var playerProxy: IMediaPlayerProxy

    fun getControllerView(inflater: LayoutInflater, container: ViewGroup?): View

    fun onPlayerStateChanged(playerState: PlayerState)

    fun onPlayerProgressChanged(positionMs: Long, totalMs: Long)

    fun onVolumeUp()

    fun onVolumeOff()
}