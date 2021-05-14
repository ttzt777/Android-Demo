package cc.bear3.android.demo.ui.demo.video.core.proxy

import cc.bear3.android.demo.manager.IDisposable
import cc.bear3.android.demo.ui.demo.video.core.controller.IExoPlayerController
import cc.bear3.android.demo.ui.demo.video.core.PlayerState
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IExoPlayerProxy : IDisposable {
    val player: ExoPlayer
    val controller: IExoPlayerController?
    var playerState: PlayerState

    fun prepare(source: MediaSource)

    fun play()

    fun pause()

    fun stop()

    fun seekTo(percent: Float)

    fun seekTo(positionMs: Long)

    fun changeVolume(volume: Int)

    fun changePlayerState(targetState: PlayerState)
}