package cc.bear3.android.demo.ui.demo.video.core

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ViewDefaultVideoPlayerViewControllerBinding
import cc.bear3.android.demo.ui.util.ext.onClick
import cc.bear3.android.demo.util.view.visible

/**
 *
 * @author TT
 * @since 2021-4-26
 */
open class DefaultVideoPlayerViewController(
    val context: Context,
    val playerProxy: IExoPlayerProxy
) : IExoPlayerViewController {
    private var binding: ViewDefaultVideoPlayerViewControllerBinding? = null

    protected val uiHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    protected val dismissRunnable: Runnable by lazy {
        Runnable { dismissControllerView() }
    }

    protected var seekBarTrackingTouchFlag = false

    companion object {
        private const val TIME_CONTROLLER_GONE_DELAY = 3000L
        private const val PROGRESS_MAX = 1000
    }

    override fun getControllerView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding =
            ViewDefaultVideoPlayerViewControllerBinding.inflate(inflater, container, false)
        bindViewClickListener(binding)
        this.binding = binding
        return binding.root
    }

    override fun onPlayerStateChanged(playerState: PlayerState) {
        removeControllerRunnable()

        binding.goneAll()

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
        if (seekBarTrackingTouchFlag || positionMs < 0 || totalMs <= 0) {
            return
        }

        binding?.let {
            val progress = positionMs.toFloat() / totalMs * PROGRESS_MAX

            it.progress.progress = progress.toInt()
            it.seekBar.progress = progress.toInt()
        }
    }

    override fun dispose() {
        removeControllerRunnable()
    }

    protected open fun changeToIdle() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_play)

            visible(it.playStatus)
        }
    }

    protected open fun changeToConfirm() {
        binding?.let {
            it.confirmHint.text = "当前是移动网络，是否继续播放"

            visible(it.confirmInfo)
        }
    }

    protected open fun changeToBuffering() {
        binding?.let {
            visible(it.loadingFlag, it.progress)
        }
    }

    protected open fun changeToPlaying() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_pause)

            visible(it.playStatus, it.bottomInfo)
        }

        postControllerRunnable()
    }

    protected open fun changeToPaused() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_play)

            visible(it.playStatus, it.bottomInfo)
        }
    }

    protected open fun changeToStop() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_play)

            visible(it.playStatus)
        }
    }

    protected open fun changeToError() {
        binding?.let {
            it.confirmHint.text = "资源错误，无法播放"

            visible(it.confirmHint)
        }
    }

    protected open fun showControllerView() {
        if (playerProxy.playerState != PlayerState.Playing) {
            return
        }

        onPlayerStateChanged(PlayerState.Playing)
    }

    protected open fun dismissControllerView() {
        binding?.goneAll()
        binding?.let {
            visible(it.progress)
        }
    }

    protected open fun postControllerRunnable() {
        removeControllerRunnable()

        uiHandler.postDelayed(dismissRunnable, TIME_CONTROLLER_GONE_DELAY)
    }

    protected open fun removeControllerRunnable() {
        uiHandler.removeCallbacks(dismissRunnable)
    }

    protected fun visible(vararg views: View) {
        for (view in views) {
            view.visible(true)
        }
    }

    protected fun gone(vararg views: View) {
        for (view in views) {
            view.visible(false)
        }
    }

    private fun bindViewClickListener(binding: ViewDefaultVideoPlayerViewControllerBinding) {
        with(binding) {
            seekBar.max = PROGRESS_MAX
            progress.max = PROGRESS_MAX

            root.onClick {
                showControllerView()
            }

            confirmButton.onClick {
                playerProxy.play()
            }

            playStatus.onClick {
                when (playerProxy.playerState) {
                    PlayerState.Playing -> playerProxy.pause()
                    else -> playerProxy.play()
                }
            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                    playerProxy.seekTo(seekBar.progress.toFloat() / seekBar.max)
                    seekBarTrackingTouchFlag = false
                }

            })
        }
    }

    private fun ViewDefaultVideoPlayerViewControllerBinding?.goneAll() {
        this?.let {
            gone(loadingFlag, confirmInfo, bottomInfo, progress)
        }
    }
}