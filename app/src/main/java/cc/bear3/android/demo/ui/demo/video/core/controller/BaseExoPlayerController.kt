package cc.bear3.android.demo.ui.demo.video.core.controller

import android.content.Context
import android.widget.SeekBar
import cc.bear3.android.demo.ui.demo.video.core.PlayerState
import cc.bear3.android.demo.ui.demo.video.core.proxy.IExoPlayerProxy

/**
 *
 * @author TT
 * @since 2021-4-26
 */
abstract class BaseExoPlayerController(
    val context: Context
) : IExoPlayerController, SeekBar.OnSeekBarChangeListener {
    override lateinit var playerProxy: IExoPlayerProxy

    protected var seekBarTrackingTouchFlag = false

    companion object {
        private const val TIME_CONTROLLER_GONE_DELAY = 3000L
        private const val PROGRESS_MAX = 1000
    }

    override fun onPlayerStateChanged(playerState: PlayerState) {
        when (playerState) {
            PlayerState.Idle -> changeToIdle()
            PlayerState.Confirm -> changeToConfirm()
            PlayerState.Buffering -> changeToBuffering()
            PlayerState.Playing -> changeToPlaying()
            PlayerState.Paused -> changeToPaused()
            PlayerState.Stop -> changeToStop()
            PlayerState.Error -> changeToError()
        }
    }

    override fun onPlayerProgressChanged(positionMs: Long, totalMs: Long) {
        updatePlayerTime(positionMs, totalMs)

        if (!seekBarTrackingTouchFlag) {
            val percent = positionMs.toFloat() / totalMs

            updateProgressPercent(percent)
        }
    }

    override fun onVolumeUp() {
    }

    override fun onVolumeOff() {
    }

    override fun dispose() {

    }

    override fun onProgressChanged(
        seekBar: SeekBar,
        progress: Int,
        fromUser: Boolean
    ) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        seekBarTrackingTouchFlag = true
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        val percent = seekBar.progress.toFloat() / seekBar.max
        updateProgressPercent(percent)
        playerProxy.seekTo(percent)
        seekBarTrackingTouchFlag = false
    }

    abstract fun changeToIdle()

    abstract fun changeToConfirm()

    abstract fun changeToBuffering()

    abstract fun changeToPlaying()

    abstract fun changeToPaused()

    abstract fun changeToStop()

    abstract fun changeToError()

    abstract fun updatePlayerTime(positionMs: Long, totalMs: Long)

    abstract fun updateProgressPercent(percent: Float)
}