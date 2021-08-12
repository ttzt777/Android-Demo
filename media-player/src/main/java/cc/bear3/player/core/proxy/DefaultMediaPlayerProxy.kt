package cc.bear3.player.core.proxy

import android.content.Context
import android.os.Handler
import android.os.Looper
import cc.bear3.player.BuildConfig
import cc.bear3.player.core.controller.IMediaPlayerController
import cc.bear3.player.core.state.IPlayerStateChangeListener
import cc.bear3.player.core.state.PlayerState
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import timber.log.Timber
import java.util.*
import kotlin.collections.HashSet

/**
 *
 * @author TT
 * @since 2021-4-26
 */
open class DefaultMediaPlayerProxy(
    protected val context: Context
) : IMediaPlayerProxy, Player.Listener {

    final override val player by lazy { SimpleExoPlayer.Builder(context).build() }
    override var playerState = PlayerState.Idle
    final override val controllers = mutableListOf<IMediaPlayerController>()

    protected val uiHandler = Handler(Looper.getMainLooper())
    protected var progressTimer: Timer? = null
    protected var progressTask: TimerTask? = null

    protected var duration = TIME_UNSET

    private val playerStateListeners: MutableSet<IPlayerStateChangeListener> by lazy {
        HashSet<IPlayerStateChangeListener>()
    }

    init {
        player.addListener(this)
    }

    companion object {
        private const val DEFAULT_PROGRESS_PERIOD = 50L
        private const val TIME_UNSET = Long.MIN_VALUE + 1
    }

    override fun prepare(source: MediaSource) {
        stop()
        reset()

        player.setMediaSource(source)
        player.prepare()
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
        changePlayerState(PlayerState.Buffering)
        player.seekTo(positionMs)
        play()
    }

    override fun volumeUp() {
        setVolume(IMediaPlayerProxy.VOLUME_ON)

        controllers.forEach {
            it.onVolumeUp()
        }
    }

    override fun volumeOff() {
        setVolume(IMediaPlayerProxy.VOLUME_OFF)

        controllers.forEach {
            it.onVolumeOff()
        }
    }

    override fun changePlayerState(targetState: PlayerState) {
        Timber.d("Player state changed -- ${playerState.name} -> ${targetState.name}")

        if (duration == TIME_UNSET) {
            duration = player.duration
        }

        this.playerState = targetState

        when (playerState) {
            PlayerState.Playing -> startProgressTimer()
            else -> cancelProgressTimer()
        }

        controllers.forEach {
            it.onPlayerStateChanged(playerState)
        }

        notifyPlayerStateChangeListeners()
    }

    override fun addPlayerStateChangedListener(listener: IPlayerStateChangeListener) {
        playerStateListeners.add(listener)
    }

    override fun removePlayerStateChangedListener(listener: IPlayerStateChangeListener) {
        playerStateListeners.remove(listener)
    }

    override fun removeAllPlayerStateChangedListener() {
        for (listener in playerStateListeners) {
            playerStateListeners.remove(listener)
        }
    }

    override fun dispose() {
        stop()
        player.release()
    }

    override fun onPlaybackStateChanged(state: Int) {
        if (BuildConfig.DEBUG) {
            val tempState = when (state) {
                Player.STATE_IDLE -> PlayerState.Idle.name
                Player.STATE_BUFFERING -> PlayerState.Buffering.name
                Player.STATE_READY -> PlayerState.Paused.name
                Player.STATE_ENDED -> PlayerState.Ended.name
                else -> ""
            }
            Timber.d("On playback state changed -- $tempState")
        }

        when (state) {
            Player.STATE_IDLE -> changePlayerState(PlayerState.Idle)
            Player.STATE_BUFFERING -> changePlayerState(PlayerState.Buffering)
            Player.STATE_READY -> changePlayerState(PlayerState.Paused)
            Player.STATE_ENDED -> changePlayerState(PlayerState.Ended)
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        Timber.d("On is playing changed -- isPlaying($isPlaying)")

        if (isPlaying) {
            changePlayerState(PlayerState.Playing)
            return
        }

        if (playerState != PlayerState.Playing) {
            return
        }

        changePlayerState(PlayerState.Paused)
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        Timber.e("On player error -- ${error.message ?: ""}")
        changePlayerState(PlayerState.Error)
    }

    protected open fun addController(controller: IMediaPlayerController) {
        if (controllers.contains(controller)) {
            return
        }

        controller.playerProxy = this
        controllers.add(controller)
    }

    protected open fun removeController(controller: IMediaPlayerController) {
        controller.dispose()
        controllers.remove(controller)
    }

    protected open fun updateProgress() {
        if (duration != TIME_UNSET) {
            controllers.forEach {
                it.onPlayerProgressChanged(player.currentPosition, duration)
            }
        }
    }

    protected open fun startProgressTimer() {
        cancelProgressTimer()

        progressTask = object : TimerTask() {
            override fun run() {
                uiHandler.post {
                    updateProgress()
                }
            }
        }

        progressTimer = Timer().apply {
            schedule(
                progressTask, 0,
                DEFAULT_PROGRESS_PERIOD
            )
        }
    }

    protected open fun cancelProgressTimer() {
        progressTask?.cancel()

        progressTimer?.cancel()
    }

    protected open fun reset() {
        duration = TIME_UNSET
    }

    protected open fun setVolume(volPercent: Float) {
        player.volume = volPercent
    }

    protected fun notifyPlayerStateChangeListeners() {
        if (playerStateListeners.size == 0) {
            return
        }

        for (listener in playerStateListeners) {
            listener.onPlayerStateChanged(playerState)
        }
    }
}