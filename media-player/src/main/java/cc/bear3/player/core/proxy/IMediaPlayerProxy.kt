package cc.bear3.player.core.proxy

import cc.bear3.player.core.state.IPlayerStateChangeListener
import cc.bear3.player.core.state.PlayerState
import cc.bear3.dispose.IDisposable
import cc.bear3.player.core.controller.IMediaPlayerController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource

/**
 * 播放器核心代理，负责处理内核、控制视图、渲染视图之间的联动
 * 一个代理对应一个播放内核，N个控制视图（可以没有），一个或没有渲染视图
 * @author TT
 * @since 2021-4-26
 */
interface IMediaPlayerProxy : IDisposable {
    companion object {
        const val VOLUME_ON = 1f
        const val VOLUME_OFF = 0f
    }

    val player: ExoPlayer
    var playerState: PlayerState
    val controllers: MutableList<IMediaPlayerController>

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