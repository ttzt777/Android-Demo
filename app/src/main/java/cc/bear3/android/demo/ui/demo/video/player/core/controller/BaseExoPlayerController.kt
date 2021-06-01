package cc.bear3.android.demo.ui.demo.video.player.core.controller

import android.content.Context
import android.view.View
import android.widget.SeekBar
import cc.bear3.android.demo.ui.demo.video.player.core.proxy.IExoPlayerProxy
import cc.bear3.android.demo.util.view.visible

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

    abstract fun updatePlayerTime(positionMs: Long, totalMs: Long)

    abstract fun updateProgressPercent(percent: Float)

    protected open fun visible(vararg views: View) {
        for (view in views) {
            view.visible(true)
        }
    }

    protected open fun gone(vararg views: View) {
        for (view in views) {
            view.visible(false)
        }
    }
}