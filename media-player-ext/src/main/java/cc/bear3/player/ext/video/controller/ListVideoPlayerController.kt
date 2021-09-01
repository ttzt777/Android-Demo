package cc.bear3.player.ext.video.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.kernal.controller.BaseMediaPlayerController
import cc.bear3.player.kernal.controller.IVideoPlayerController
import cc.bear3.player.kernal.protocol.IVideoEntity
import cc.bear3.player.kernal.state.PlayerState
import cc.bear3.player.kernal.util.formatMediaTime
import cc.bear3.player.ext.R
import cc.bear3.player.ext.databinding.ViewListVideoPlayerViewControllerBinding
import com.bumptech.glide.Glide

/**
 *
 * @author TT
 * @since 2021-5-24
 */
class ListVideoPlayerController(context: Context) :
    BaseMediaPlayerController(context),
    IVideoPlayerController {

    private lateinit var binding: ViewListVideoPlayerViewControllerBinding

    override fun updatePlayerTime(positionMs: Long, totalMs: Long) {
        var remaining = totalMs - positionMs
        if (remaining < 0) {
            remaining = 0
        }

        binding.totalText.text = formatMediaTime(remaining)
        visible(binding.totalText)
    }

    override fun updateProgressPercent(percent: Float) {
        with(binding.progress) {
            progress = (percent * max).toInt()
        }
    }

    override fun getControllerView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (this::binding.isInitialized) {
            return binding.root
        }

        binding = ViewListVideoPlayerViewControllerBinding.inflate(inflater, container, false)
        initView(binding)
        return binding.root
    }

    override fun onPlayerStateChanged(playerState: PlayerState) {
        goneAll()

        when (playerState) {
            PlayerState.Buffering -> changeToBuffering()
            PlayerState.Playing -> changeToPlaying()
            else -> changeToIdle()
        }
    }

    override fun onVideoEntityPrepared(entity: IVideoEntity) {
        binding.let {
            Glide.with(it.bgImage).load(entity.coverUrl).into(it.bgImage)
        }
    }

    private fun changeToIdle() {
        with(binding) {
            visible(bgImage, playStatus)
        }
    }

    private fun changeToBuffering() {
        with(binding) {
            visible(loadingFlag)
        }
    }

    private fun changeToPlaying() {
        with(binding) {
            visible(progress)
        }
    }

    private fun initView(binding: ViewListVideoPlayerViewControllerBinding) {
        binding.playStatus.setImageResource(R.drawable.ic_video_play)
        binding.progress.max = PROGRESS_MAX
        gone(binding.totalText)
    }

    private fun goneAll() {
        with(binding) {
            gone(bgImage, loadingFlag, playStatus, progress, totalText)
        }
    }
}