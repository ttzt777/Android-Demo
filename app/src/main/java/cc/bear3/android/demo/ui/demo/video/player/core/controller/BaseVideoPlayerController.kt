package cc.bear3.android.demo.ui.demo.video.player.core.controller

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import cc.bear3.android.demo.ui.demo.video.player.core.PlayerState
import cc.bear3.android.demo.ui.demo.video.player.core.proxy.IVideoPlayerProxy
import cc.bear3.android.demo.util.view.visible

/**
 *
 * @author TT
 * @since 2021-4-26
 */
abstract class BaseVideoPlayerController(
    context: Context
) : BaseExoPlayerController(context), IVideoPlayerController {

    protected var fullScreenFlag = false

    protected var volumeUpFlag = true

    protected val uiHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    protected val dismissRunnable: Runnable by lazy {
        Runnable { dismissControllerView() }
    }

    protected var controllerViewShowingFlag = false

    protected var playerView: View? = null
    protected var playerParent: ViewGroup? = null

    protected var systemVisibility: Int = 0

    companion object {
        private const val TIME_CONTROLLER_GONE_DELAY = 3000L
    }

    override fun onPlayerStateChanged(playerState: PlayerState) {
        removeControllerRunnable()

        controllerViewShowingFlag = true

        super.onPlayerStateChanged(playerState)
    }

    override fun onVolumeUp() {
        volumeUpFlag = true
    }

    override fun onVolumeOff() {
        volumeUpFlag = false
    }

    override fun dispose() {
        removeControllerRunnable()
    }

    override fun onEnterFullScreen() {
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

    override fun onExitFullScreen() {
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

    protected open fun showControllerView(): Boolean {
        if (playerProxy.playerState == PlayerState.Buffering) {
            return false
        }

        controllerViewShowingFlag = true

        onPlayerStateChanged(playerProxy.playerState)

        return true
    }

    protected open fun dismissControllerView(): Boolean {
        if (playerProxy.playerState == PlayerState.Buffering) {
            return false
        }

        removeControllerRunnable()

        controllerViewShowingFlag = false

        return true
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

    protected open fun toggleFullScreen() {
        if (fullScreenFlag) {
            onExitFullScreen()
        } else {
            onEnterFullScreen()
        }
    }

    protected open fun changeFullScreenFlag(target: Boolean) {
        fullScreenFlag = target

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
}