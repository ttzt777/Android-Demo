package cc.bear3.android.demo.ui.demo.video.core

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import timber.log.Timber
import java.util.*

/**
 *
 * @author TT
 * @since 2021-4-26
 */
open class DefaultExoPlayerProxy(
    protected val context: Context
) : IExoPlayerProxy, Player.EventListener {

    override val player by lazy { SimpleExoPlayer.Builder(context).build() }
    override var playerState = PlayerState.Idle

    override var viewController: IExoPlayerViewController? = null

    protected val uiHandler = Handler(Looper.getMainLooper())
    protected var progressTimer: Timer? = null
    protected val progressTask: TimerTask

    protected var duration = TIME_UNSET

    init {
        progressTask = object : TimerTask() {
            override fun run() {
                uiHandler.post {
                    updateProgress()
                }
            }
        }
    }

    companion object {
        private const val DEFAULT_PROGRESS_PERIOD = 50L
        private const val TIME_UNSET = Long.MIN_VALUE + 1
    }

    override fun prepare(source: MediaSource, autoPlay: Boolean) {
        stop()
        reset()

        player.setMediaSource(source)
        player.prepare()
        player.playWhenReady = autoPlay
    }

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun stop() {
        player.stop()
    }

    override fun seekTo(percent: Float) {
        seekTo(positionMs = (percent * player.duration).toLong())
    }

    override fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
    }

    override fun changeVolume(volume: Int) {

    }

    override fun changePlayerState(targetState: PlayerState) {
        Timber.d("Player state changed ${playerState.name} -> ${targetState.name}")

        if (duration == TIME_UNSET) {
            duration = player.duration
        }

        this.playerState = targetState

        when (playerState) {
            PlayerState.Playing -> startProgressTimer()
            else -> cancelProgressTimer()
        }

        viewController?.onPlayerStateChanged(playerState)
    }

    override fun dispose() {
        stop()
        player.release()
    }

    override fun onPlaybackStateChanged(state: Int) {
        when (state) {
            Player.STATE_IDLE -> changePlayerState(PlayerState.Idle)
            Player.STATE_BUFFERING -> changePlayerState(PlayerState.Buffering)
            Player.STATE_READY -> changePlayerState(PlayerState.Paused)
            Player.STATE_ENDED -> changePlayerState(PlayerState.Stop)
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        changePlayerState(
            if (isPlaying) {
                PlayerState.Playing
            } else {
                PlayerState.Paused
            }
        )
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        changePlayerState(PlayerState.Error)
    }

    protected open fun updateProgress() {
        if (duration != TIME_UNSET) {
            viewController?.onPlayerProgressChanged(player.currentPosition, duration)
        }
    }

    protected open fun startProgressTimer() {
        if (viewController == null) {
            return
        }

        if (progressTimer != null) {
            return
        }

        progressTimer = Timer().apply {
            schedule(progressTask, 0, DEFAULT_PROGRESS_PERIOD)
        }
    }

    protected open fun cancelProgressTimer() {
        progressTask.cancel()

        progressTimer?.let {
            it.cancel()
            progressTimer = null
        }
    }

    protected open fun reset() {
        duration = TIME_UNSET
    }
}