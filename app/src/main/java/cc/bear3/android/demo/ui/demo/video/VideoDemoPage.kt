package cc.bear3.android.demo.ui.demo.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.PageVideoDemoBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.demo.video.player.autoplay.builder.AutoPlayerControllerBuilder
import cc.bear3.android.demo.ui.demo.video.player.autoplay.controller.ListAutoplayController
import cc.bear3.android.demo.ui.demo.video.player.core.data.createVideoEntityList
import cc.bear3.android.demo.util.context.startWithAnim
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-4-25
 */
class VideoDemoPage : BaseActivity() {
    private lateinit var binding: PageVideoDemoBinding

    override fun initView(savedInstanceState: Bundle?) {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@VideoDemoPage)
            val rvAdapter = VideoListAdapter()
            adapter = rvAdapter
            rvAdapter.dataRefresh(createVideoEntityList())
        }

        lifecycle.addObserver(Observer())

        AutoPlayerControllerBuilder
            .newBuilder(ListAutoplayController::class.java)
            .setRecyclerView(binding.recyclerView)
            .setVideoPlayerId(R.id.playerView)
            .monitor(lifecycle)
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = PageVideoDemoBinding.inflate(inflater, container, false)
        return binding.root
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