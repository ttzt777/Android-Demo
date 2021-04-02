package cc.bear3.android.demo.ui.demo.channel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import cc.bear3.android.demo.databinding.PageChannelDemoBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.util.ext.onClick
import cc.bear3.android.demo.util.context.startWithAnim
import com.google.android.material.tabs.TabLayoutMediator

/**
 *
 * @author TT
 * @since 2021-3-11
 */
class ChannelDemoPage : BaseActivity() {

    private lateinit var binding: PageChannelDemoBinding
    private lateinit var adapter: ChannelPagerAdapter

    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = PageChannelDemoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter = ChannelPagerAdapter(this)
        binding.pager.adapter = adapter
        tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = adapter.getChannelData(position).name
        }.apply {
            attach()
        }

        binding.pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                val data = adapter.getChannelData(position)
                if (data != ChannelManager.currentChannel.value) {
                    ChannelManager.currentChannel.value = data
                }
            }
        })

        ChannelManager.observeChannelList(this) {
            adapter.updateData(it)
            moveToTab(ChannelManager.currentChannel.value)
        }

        ChannelManager.currentChannel.observe(this,
            Observer {
                moveToTab(it)
            })

        binding.channelManager.onClick {
            ChannelManagerPage.invoke(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator?.detach()
    }

    private fun moveToTab(data: ChannelData?) {
        data?.let {
            binding.pager.currentItem = adapter.getChannelPosition(it)
        }
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, ChannelDemoPage::class.java)
            context.startWithAnim(intent)
        }
    }
}