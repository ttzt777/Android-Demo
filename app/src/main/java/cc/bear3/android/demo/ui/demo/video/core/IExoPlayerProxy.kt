package cc.bear3.android.demo.ui.demo.video.core

import cc.bear3.android.demo.manager.IDisposable
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IExoPlayerProxy : IDisposable {
    val player: ExoPlayer
    var playerState: PlayerState
    var viewController: IExoPlayerViewController?

    fun prepare(source: MediaSource, autoPlay: Boolean = true)

    fun play()

    fun pause()

    fun stop()

    fun seekTo(percent: Float)

    fun seekTo(positionMs: Long)

    fun changeVolume(volume: Int)

    fun changePlayerState(targetState: PlayerState)
}