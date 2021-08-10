package cc.bear3.player.core.controller

import android.content.Context
import android.media.AudioManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import cc.bear3.android.demo.ui.demo.video.player.core.state.PlayerState
import cc.bear3.android.demo.ui.demo.video.player.core.view.VideoControllerEventView
import cc.bear3.player.R
import cc.bear3.player.core.data.IVideoProtocol
import cc.bear3.player.databinding.ViewDefaultVideoPlayerViewControllerBinding
import cc.bear3.util.utils.date.DateUtil
import cc.bear3.util.utils.view.onClick
import com.bumptech.glide.Glide
import timber.log.Timber


/**
 *
 * @author TT
 * @since 2021-4-26
 */
open class DefaultVideoPlayerController(
    context: Context
) : BaseVideoPlayerController(context), SeekBar.OnSeekBarChangeListener {
    private var binding: ViewDefaultVideoPlayerViewControllerBinding? = null

    companion object {
        private const val PROGRESS_MAX = 1000
    }

    override fun getControllerView(inflater: LayoutInflater, container: ViewGroup?): View {
        if (binding != null) {
            return binding!!.root
        }

        val binding =
            ViewDefaultVideoPlayerViewControllerBinding.inflate(inflater, container, false)
        bindViewClickListener(binding)
        this.binding = binding
        return binding.root
    }

    override fun onPlayerStateChanged(playerState: PlayerState) {
        binding.goneAll()

        super.onPlayerStateChanged(playerState)
    }

    override fun onVolumeUp() {
        binding?.volumeFlag?.setImageResource(R.drawable.ic_video_volume_up)

        super.onVolumeUp()
    }

    override fun onVolumeOff() {
        binding?.volumeFlag?.setImageResource(R.drawable.ic_video_volume_off)

        super.onVolumeOff()
    }

    override fun onVideoEntityPrepared(entity: IVideoProtocol) {
        binding?.let {
            Glide.with(it.bgImage).load(entity.url).into(it.bgImage)
        }
    }

    override fun changeToIdle() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_play)

            visible(it.bgImage, it.playStatus)
        }
    }

    override fun changeToConfirm() {
        binding?.let {
            it.confirmHint.text = "当前是移动网络，是否继续播放"

            visible(it.confirmInfo)
        }
    }

    override fun changeToBuffering() {
        binding?.let {
            visible(it.loadingFlag, it.progress)
        }
    }

    override fun changeToPlaying() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_pause)

            visible(it.playStatus, it.bottomInfo)
        }

        postControllerRunnable()
    }

    override fun changeToPaused() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_play)

            visible(it.playStatus, it.bottomInfo)
        }
    }

    override fun changeToStop() {
        binding?.let {
            it.playStatus.setImageResource(R.drawable.ic_video_play)

            visible(it.playStatus)
        }
    }

    override fun changeToError() {
        binding?.let {
            it.confirmHint.text = "资源错误，无法播放"

            visible(it.confirmHint)
        }
    }

    override fun updatePlayerTime(positionMs: Long, totalMs: Long) {
        if (positionMs < 0 || totalMs <= 0) {
            return
        }

        binding?.let {
            it.positionText.text = DateUtil.formatMediaTime(positionMs)
            it.totalText.text = DateUtil.formatMediaTime(totalMs)
        }
    }

    override fun updateProgressPercent(percent: Float) {
        binding?.seekBar?.let {
            it.progress = (percent * it.max).toInt()
        }

        binding?.progress?.let {
            it.progress = (percent * it.max).toInt()
        }
    }

    override fun dismissControllerView(): Boolean {
        if (super.dismissControllerView()) {
            binding?.goneAll()
            binding?.let {
                visible(it.progress)
            }

            return true
        }

        return false
    }


    override fun changeFullScreenFlag(target: Boolean) {
        super.changeFullScreenFlag(target)

        binding?.fullScreen?.setImageResource(
            if (fullScreenFlag) R.drawable.ic_video_fullscreen_exit else R.drawable.ic_video_full_screen_enter
        )
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
                        onExitFullScreen()
                        true
                    } else {
                        false
                    }
                }

                override fun onVolumeUpClick() {
                    playerProxy.volumeUp()

                    if (BuildConfig.DEBUG) {
                        val am =
                            context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
                                ?: return

                        val volume = am.getStreamVolume(AudioManager.STREAM_MUSIC)

                        Timber.i("volume up click -- $volume")
                    }
                }

                override fun onVolumeDownClick() {
                    val am =
                        context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return

                    val volume = am.getStreamVolume(AudioManager.STREAM_MUSIC)

                    Timber.d("volume down click -- $volume")

                    if (volume <= 0) {
                        playerProxy.volumeOff()
                    }
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

            volumeFlag.onClick {
                if (volumeUpFlag) {
                    playerProxy.volumeOff()
                } else {
                    playerProxy.volumeUp()
                }
            }

            seekBar.setOnSeekBarChangeListener(this@DefaultVideoPlayerController)

            fullScreen.onClick {
                toggleFullScreen()
            }
        }
    }

    private fun ViewDefaultVideoPlayerViewControllerBinding?.goneAll() {
        this?.let {
            gone(
                it.bgImage,
                it.loadingFlag,
                it.playStatus,
                it.confirmInfo,
                it.bottomInfo,
                it.progress
            )
        }
    }
}