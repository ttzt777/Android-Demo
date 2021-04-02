package cc.bear3.android.demo.ui.demo.channel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import cc.bear3.android.demo.databinding.PageChannelManagerBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim

/**
 *
 * @author TT
 * @since 2021-3-15
 */
class ChannelManagerPage : BaseActivity() {
    private lateinit var binding: PageChannelManagerBinding

    private val adapter by lazy {
        ChannelManagerAdapter(this)
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = PageChannelManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        with(binding) {
            list.layoutManager = LinearLayoutManager(this@ChannelManagerPage)
            list.adapter = adapter
        }

        adapter.updateData(
            ChannelManagerData.newInstance(
                ChannelManagerType.Subscribed,
                ChannelManager.channelList.value!!.toMutableList()
            )
        )

        val suggestChannelList = ChannelManager.requestSuggestChannelList()
        if (suggestChannelList.isNotEmpty()) {
            adapter.updateData(
                ChannelManagerData.newInstance(
                    ChannelManagerType.Suggest,
                    suggestChannelList.toMutableList()
                ), false
            )
        }
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, ChannelManagerPage::class.java)
            context.startWithAnim(intent)
        }
    }
}