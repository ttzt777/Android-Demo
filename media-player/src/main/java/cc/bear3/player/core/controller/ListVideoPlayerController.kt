package cc.bear3.player.core.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.player.core.state.PlayerState
import cc.bear3.player.R
import cc.bear3.player.core.data.IVideoProtocol
import cc.bear3.player.databinding.ViewListVideoPlayerViewControllerBinding
import cc.bear3.util.utils.date.DateUtil
import com.bumptech.glide.Glide

/**
 *
 * @author TT
 * @since 2021-5-24
 */
class ListVideoPlayerController(context: Context) : BaseExoPlayerController(context),
    IVideoPlayerController {

    private lateinit var binding: ViewListVideoPlayerViewControllerBinding

    override fun updatePlayerTime(positionMs: Long, totalMs: Long) {
        var remaining = totalMs - positionMs
        if (remaining < 0) {
            remaining = 0
        }

        binding.totalText.text = DateUtil.formatMediaTime(remaining)
        visible(binding.totalText)
    }

    override fun updateProgressPercent(percent: Float) {
        with(binding.progress) {
            progress = (percent * max).toInt()
        }
    }

    override fun onVideoEntityPrepared(entity: IVideoProtocol) {
        Glide.with(binding.bgImage).load(entity.url).into(binding.bgImage)
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

    companion object {
        private const val PROGRESS_MAX = 1000
    }
}