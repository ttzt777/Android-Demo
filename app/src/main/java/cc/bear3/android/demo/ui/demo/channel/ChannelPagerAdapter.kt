package cc.bear3.android.demo.ui.demo.channel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-3-15
 */
class ChannelPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val channelList = mutableListOf<ChannelData>()
    private val cacheFragmentList = mutableMapOf<ChannelData, Fragment>()

    override fun getItemCount(): Int {
        return channelList.size
    }

    override fun createFragment(position: Int): Fragment {
        Timber.d("Create fragment position is $position, channel name is ${getChannelData(position).name}")

        val channelData = getChannelData(position)
        val fragment = cacheFragmentList[channelData] ?: createFragment(channelData)
        cacheFragmentList[channelData] = fragment
        return fragment
    }

    override fun getItemId(position: Int): Long {
        return channelList[position].hashCode().toLong()
    }

    fun getChannelData(position: Int): ChannelData {
        return channelList[position]
    }

    fun updateData(channelList: List<ChannelData>) {
        this.channelList.clear()

        this.channelList.addAll(channelList)

        notifyDataSetChanged()
    }

    private fun createFragment(channelData: ChannelData): Fragment {
        return ChannelItemFragment.newInstance(channelData)
    }
}