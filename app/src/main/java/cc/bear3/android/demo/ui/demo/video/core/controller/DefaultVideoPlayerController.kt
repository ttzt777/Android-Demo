package cc.bear3.android.demo.ui.demo.video.core.controller

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.SeekBar
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ViewDefaultVideoPlayerViewControllerBinding
import cc.bear3.android.demo.ui.demo.video.core.PlayerState
import cc.bear3.android.demo.ui.demo.video.core.proxy.IExoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.core.proxy.IVideoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.core.view.VideoControllerEventView
import cc.bear3.android.demo.ui.util.ext.onClick
import cc.bear3.android.demo.util.date.DateUtil
import cc.bear3.android.demo.util.view.visible

/**
 *
 * @author TT
 * @since 2021-4-26
 */
open class DefaultVideoPlayerController(
    val context: Context
) : IVideoPlayerController, SeekBar.OnSeekBarChangeListener {
    override lateinit var playerProxy: IExoPlayerProxy
    override var fullScreenFlag = false
    private var binding: ViewDefaultVideoPlayerViewControllerBinding? = null

    protected val uiHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    protected val dismissRunnable: Runnable by lazy {
        Runnable { dismissControllerView() }
    }

    protected var seekBarTrackingTouchFlag = false
    protected var controllerViewShowingFlag = false

    protected var playerView: View? = null
    protected var playerParent: ViewGroup? = null

    protected var systemVisibility: Int = 0

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

        controllerViewShowingFlag = true

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
        updatePlayerTime(positionMs, totalMs)

        if (!seekBarTrackingTouchFlag) {
            val percent = positionMs.toFloat() / totalMs

            updateSeekBarPercent(percent)
            updateProgressPercent(percent)
        }
    }

    override fun dispose() {
        removeControllerRunnable()
    }

    override fun onProgressChanged(
        seekBar: SeekBar,
        progress: Int,
        fromUser: Boolean
    ) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        seekBarTrackingTouchFlag = true

        removeControllerRunnable()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        val percent = seekBar.progress.toFloat() / seekBar.max
        updateProgressPercent(percent)
        playerProxy.seekTo(percent)
        seekBarTrackingTouchFlag = false
    }

    override fun enterFullScreen() {
        if (fullScreenFlag) {
            return
        }

        val playerProxy = this.playerProxy as? IVideoPlayerProxy ?: return
        val activity = context as? Activity ?: return

        val player = playerProxy.getWrapperView()
        val parent = player.parent as? ViewGroup ?: return

        this.playerView = player
        this.playerParent = parent

        changeFullScreenFlag(true)

        // 从原来的地方移除
        parent.removeView(player)

        // 添加到Activity的content中
        val activityContent = activity.findViewById(android.R.id.content) as FrameLayout
        activityContent.addView(
            player,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        // 进入全屏状态
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        systemVisibility = activity.window.decorView.systemUiVisibility
//        activity.window.decorView.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    override fun exitFullScreen() {
        if (!fullScreenFlag) {
            return
        }

        val activity = context as? Activity ?: return
        val activityContent = activity.findViewById(android.R.id.content) as FrameLayout

        val parent = playerParent ?: return
        val player = playerView ?: return

        playerParent = null
        playerView = null

        changeFullScreenFlag(false)

        // 从Activity的Content中移除
        activityContent.removeView(player)

        // 添加到原来的布局中
        parent.addView(
            player,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        // 退出全屏状态
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        activity.window.decorView.systemUiVisibility = systemVisibility
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
        if (playerProxy.playerState == PlayerState.Buffering) {
            return
        }

        controllerViewShowingFlag = true

        onPlayerStateChanged(playerProxy.playerState)
    }

    protected open fun dismissControllerView() {
        if (playerProxy.playerState == PlayerState.Buffering) {
            return
        }

        removeControllerRunnable()

        binding?.goneAll()
        binding?.let {
            visible(it.progress)
        }

        controllerViewShowingFlag = false
    }

    protected open fun toggleControllerView() {
        if (controllerViewShowingFlag) {
            dismissControllerView()
        } else {
            showControllerView()
        }
    }

    protected open fun postControllerRunnable() {
        removeControllerRunnable()

        uiHandler.postDelayed(dismissRunnable, TIME_CONTROLLER_GONE_DELAY)
    }

    protected open fun removeControllerRunnable() {
        uiHandler.removeCallbacks(dismissRunnable)
    }

    protected open fun updatePlayerTime(positionMs: Long, totalMs: Long) {
        if (positionMs < 0 || totalMs <= 0) {
            return
        }

        binding?.let {
            it.positionText.text = DateUtil.formatTime(positionMs)
            it.totalText.text = DateUtil.formatTime(totalMs)
        }
    }

    protected open fun updateSeekBarPercent(percent: Float) {
        binding?.seekBar?.let {
            it.progress = (percent * it.max).toInt()
        }
    }

    protected open fun updateProgressPercent(percent: Float) {
        binding?.progress?.let {
            it.progress = (percent * it.max).toInt()
        }
    }

    protected open fun toggleFullScreen() {
        if (fullScreenFlag) {
            exitFullScreen()
        } else {
            enterFullScreen()
        }
    }

    protected open fun changeFullScreenFlag(target: Boolean) {
        fullScreenFlag = target

        binding?.fullScreen?.setImageResource(
            if (fullScreenFlag) R.drawable.ic_video_fullscreen_exit else R.drawable.ic_video_full_screen_enter
        )

        changeOrientationIfNeeded()
    }

    protected open fun changeOrientationIfNeeded() {
        val playerRenderer = (playerProxy as? IVideoPlayerProxy)?.renderer ?: return

        if (playerRenderer.videoWidth == 0f || playerRenderer.videoHeight == 0f) {
            return
        }

        if (playerRenderer.videoWidth >= playerRenderer.videoHeight) {
            // 切换方向
            val activity = context as? Activity ?: return

            activity.requestedOrientation = if (fullScreenFlag) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
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

    protected fun ViewDefaultVideoPlayerViewControllerBinding?.goneAll() {
        this?.let {
            gone(it.loadingFlag, it.playStatus, it.confirmInfo, it.bottomInfo, it.progress)
        }
    }

    private fun bindViewClickListener(binding: ViewDefaultVideoPlayerViewControllerBinding) {
        with(binding) {
            seekBar.max = PROGRESS_MAX
            progress.max = PROGRESS_MAX

            touchView.onClick {
                toggleControllerView()
            }
            touchView.callback = object : VideoControllerEventView.Callback {
                override fun onBackClick(): Boolean {
                    return if (fullScreenFlag) {
                        exitFullScreen()
                        true
                    } else {
                        false
                    }
                }

                override fun onVolumeUpClick() {

                }

                override fun onVolumeDownClick() {

                }
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

            seekBar.setOnSeekBarChangeListener(this@DefaultVideoPlayerController)

            fullScreen.onClick {
                toggleFullScreen()
            }
        }
    }
}