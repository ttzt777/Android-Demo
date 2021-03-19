package cc.bear3.android.demo.ui.demo.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.databinding.FragmentChannelItemBinding
import cc.bear3.android.demo.ui.base.BaseFragment

/**
 *
 * @author TT
 * @since 2021-3-11
 */
class ChannelItemFragment : BaseFragment() {
    private lateinit var binding: FragmentChannelItemBinding

    private lateinit var channelData: ChannelData
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        initParams(savedInstanceState) {
            channelData = it.getParcelable(ARG_CHANNEL_DATA)!!
        }

        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(ARG_CHANNEL_DATA, channelData)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentChannelItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.title.text = channelData.name
        binding.count.text = count.toString()
        binding.root.setOnClickListener() {
            count++
            binding.count.text = count.toString()
        }
    }

    override fun getTagName(): String {
        return "${javaClass.simpleName} - ${channelData.name}"
    }

    companion object {
        private const val ARG_CHANNEL_DATA = "arg_channel_data"

        fun newInstance(data: ChannelData): ChannelItemFragment {
            return ChannelItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHANNEL_DATA, data)
                }
            }
        }
    }
}