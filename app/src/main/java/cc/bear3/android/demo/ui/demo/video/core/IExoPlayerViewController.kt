package cc.bear3.android.demo.ui.demo.video.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.manager.IDisposable

/**
 *
 * @author TT
 * @since 2021-4-26
 */
interface IExoPlayerViewController : IDisposable{
    fun getControllerView(inflater: LayoutInflater, container: ViewGroup?): View

    fun onPlayerStateChanged(playerState: PlayerState)

    fun onPlayerProgressChanged(positionMs: Long, totalMs: Long)
}