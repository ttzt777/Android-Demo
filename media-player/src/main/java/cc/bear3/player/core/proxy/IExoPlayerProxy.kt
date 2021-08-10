package cc.bear3.player.core.proxy

import cc.bear3.android.demo.ui.demo.video.player.core.state.IPlayerStateChangeListener
import cc.bear3.android.demo.ui.demo.video.player.core.state.PlayerState
import cc.bear3.dispose.IDisposable
import cc.bear3.player.core.controller.IExoPlayerController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IExoPlayerProxy : IDisposable {
    companion object {
        const val VOLUME_ON = 1f
        const val VOLUME_OFF = 0f
    }

    val player: ExoPlayer
    val controller: IExoPlayerController?
    var playerState: PlayerState

    fun prepare(source: MediaSource)

    fun play()

    fun pause()

    fun stop()

    fun seekTo(percent: Float)

    fun seekTo(positionMs: Long)

    fun volumeUp()

    fun volumeOff()

    fun changePlayerState(targetState: PlayerState)

    fun addPlayerStateChangedListener(listener: IPlayerStateChangeListener)

    fun removePlayerStateChangedListener(listener: IPlayerStateChangeListener)

    fun removeAllPlayerStateChangedListener()
}