package cc.bear3.android.demo.ui.demo.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ItemVideoListBinding
import cc.bear3.android.demo.databinding.PageVideoDemoBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.base.SingleFastAdapter
import cc.bear3.android.demo.ui.demo.video.player.VideoEntity
import cc.bear3.android.demo.ui.demo.video.player.createVideoEntityList
import cc.bear3.android.demo.util.context.startWithAnim
import cc.bear3.player.autoplay.builder.AutoPlayerControllerBuilder
import cc.bear3.player.autoplay.controller.ListAutoplayController
import cc.bear3.util.utils.view.onClick
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-4-25
 */
class VideoDemoPage : BaseActivity<PageVideoDemoBinding>(PageVideoDemoBinding::inflate) {
    private val adapter by lazy {
        object :
            SingleFastAdapter<VideoEntity, ItemVideoListBinding>(ItemVideoListBinding::inflate) {
            override fun convert(binding: ItemVideoListBinding, data: VideoEntity) {
                binding.playerView.updateData(data)

                binding.playerView.onClick {
                    VideoDetailPage.invoke(binding.playerView.context, data)
                }
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@VideoDemoPage)
            adapter = this@VideoDemoPage.adapter
        }

        adapter.dataRefresh(createVideoEntityList())

        lifecycle.addObserver(Observer())

        AutoPlayerControllerBuilder
            .newBuilder(ListAutoplayController::class.java)
            .setRecyclerView(binding.recyclerView)
            .setVideoPlayerId(R.id.playerView)
            .monitor(lifecycle)
    }

    class Observer : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            Timber.d("Lifecycle state changed -- state(${source.lifecycle.currentState.name}), event(${event.name})")
        }
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, VideoDemoPage::class.java)
            context.startWithAnim(intent)
        }
    }
}