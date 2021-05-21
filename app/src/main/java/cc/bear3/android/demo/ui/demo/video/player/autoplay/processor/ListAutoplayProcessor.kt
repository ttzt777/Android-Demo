package cc.bear3.android.demo.ui.demo.video.player.autoplay.processor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.bear3.android.demo.ui.demo.video.player.autoplay.data.AutoplayControllerParam
import cc.bear3.android.demo.ui.demo.video.player.core.PlayerState
import cc.bear3.android.demo.ui.demo.video.player.core.proxy.IExoPlayerProxy
import cc.bear3.android.demo.ui.demo.video.player.view.IVideoPlayerView
import timber.log.Timber
import kotlin.math.abs

/**
 *
 * @author TT
 * @since 2021-5-21
 */
class ListAutoplayProcessor(controllerParam: AutoplayControllerParam) :
    DefaultAutoplayProcessor(controllerParam), IListAutoplayProcessor {

    private val viewLocalVisibleRect = Rect()

    override fun start() {
        // 找到第一个满足条件的
        val layoutManager =
            (controllerParam.recyclerView?.layoutManager as? LinearLayoutManager) ?: return
        val firstItemPos = layoutManager.findFirstVisibleItemPosition()
        val lastItemPos = layoutManager.findLastVisibleItemPosition()

        if (firstItemPos == RecyclerView.NO_POSITION) {
            dispose()
            return
        }

        var targetPlayerProxy: IExoPlayerProxy? = null

        for (index in firstItemPos..lastItemPos) {
            val itemView = layoutManager.findViewByPosition(index) ?: continue
            val playerView = itemView.findViewById<View>(controllerParam.videoPlayerId) ?: continue
            if (playerView.visibility != View.VISIBLE || playerView.height <= 0) {
                continue
            }
            val tempPlayerProxy =
                (playerView as? IVideoPlayerView)?.getVideoPlayerProxy() ?: continue

            playerView.getLocalVisibleRect(viewLocalVisibleRect)
            Timber.d("Video player view local rect by start -- $viewLocalVisibleRect")

            if (viewLocalVisibleRect.top < 0 || viewLocalVisibleRect.bottom <= 0) {
                continue
            }

            if (viewLocalVisibleRect.top > 0) {
                // 顶部被遮挡
                val ratio = viewLocalVisibleRect.top.toFloat() / playerView.height

                if (ratio < 0.2f) {
                    targetPlayerProxy = tempPlayerProxy
                    break
                }
            } else {
                // viewLocalVisibleRect.top == 0 顶部显示，底部被遮挡或者全部显示
                targetPlayerProxy = tempPlayerProxy
                break
            }
        }

        if (crtPlayerProxy != null) {
            dispose()
        }
        if (targetPlayerProxy != null) {
            targetPlayerProxy.play()
            crtPlayerProxy = targetPlayerProxy
        }
    }

    override fun pauseByScroll() {
        val playerProxy = crtPlayerProxy ?: return
        if (playerProxy.playerState != PlayerState.Playing) {
            return
        }
        val layoutManager =
            (controllerParam.recyclerView?.layoutManager as? LinearLayoutManager) ?: return

        // 判断当前的播放器是否滑出屏幕外
        val firstItemPos = layoutManager.findFirstVisibleItemPosition()
        val lastItemPos = layoutManager.findLastVisibleItemPosition()

        if (firstItemPos == RecyclerView.NO_POSITION) {
            playerProxy.pause()
            return
        }

        for (index in firstItemPos..lastItemPos) {
            val itemView = layoutManager.findViewByPosition(index) ?: continue
            val playerView = itemView.findViewById<View>(controllerParam.videoPlayerId) ?: continue
            if (playerView.visibility != View.VISIBLE) {
                continue
            }
            val tempPlayerProxy =
                (playerView as? IVideoPlayerView)?.getVideoPlayerProxy() ?: continue

            if (tempPlayerProxy != playerProxy) {
                continue
            }

            // 只处理和当前相同的播放器，需要判定是否滑出屏幕外，如果滑出就暂停；如果没有滑出，不做处理
            playerView.getLocalVisibleRect(viewLocalVisibleRect)
            Timber.d("Video player view local rect by pauseByScroll -- $viewLocalVisibleRect")

            if (viewLocalVisibleRect.bottom <= 0) {
                dispose()
            }
            break
        }
    }
}