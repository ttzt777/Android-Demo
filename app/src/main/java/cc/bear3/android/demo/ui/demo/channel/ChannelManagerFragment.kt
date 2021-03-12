package cc.bear3.android.demo.ui.demo.channel

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.databinding.FragmentChannelManagerBinding
import cc.bear3.android.demo.ui.base.BaseFragment
import cc.bear3.android.demo.util.viewpager.ViewPagerAdapter

/**
 *
 * @author TT
 * @since 2021-3-11
 */
class ChannelManagerFragment : BaseFragment(){

    private lateinit var binding: FragmentChannelManagerBinding

    private val adapter by lazy {
        ViewPagerAdapter<ChannelItemFragment>(childFragmentManager)
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentChannelManagerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pager.adapter = this.adapter
        binding.tabs.setupWithViewPager(binding.pager)

        ChannelManager.observeChannelList(this) {
            val titleList = mutableListOf<String>()
            val fragmentList = mutableListOf<ChannelItemFragment>()

            for (channelData in it) {
                titleList.add(channelData.name)
                fragmentList.add(ChannelItemFragment.newInstance(channelData))
            }
            adapter.setFragments(titleList, fragmentList)
        }

        Handler().postDelayed({ChannelManager.change()}, 5000)
    }
}