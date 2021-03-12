package cc.bear3.android.demo.ui.demo.channel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 *
 * @author TT
 * @since 2021-3-11
 */
object ChannelManager {
    private val channelList = MutableLiveData<List<ChannelData>>()

    init {
        init()
    }

    fun observeChannelList(owner: LifecycleOwner, onChanged: (List<ChannelData>) -> Unit) {
        val wrappedObserver = Observer<List<ChannelData>> { t -> onChanged.invoke(t) }
        channelList.observe(owner, wrappedObserver)
    }

    fun init() {
        // 从本地拉取
        getChannelListFromLocal()

        // 模拟从服务器拉取
        getChannelListFromServer()
    }

    private fun getChannelListFromLocal() {
        val testChannelList = listOf(
            ChannelData(0, "推荐"),
            ChannelData(1, "我的最爱"),
            ChannelData(2, "哈哈哈哈"),
            ChannelData(3, "本地频道"),
            ChannelData(4, "我是名字超级长的频道")
        )
        channelList.value = testChannelList
    }

    private fun getChannelListFromServer() {

    }

    private fun saveChannelListToLocal() {

    }

    fun change() {
        val testChannelList = listOf(
            ChannelData(5, "寒江孤影"),
            ChannelData(6, "江湖故人"),
            ChannelData(7, "相逢何必曾相识")
        )
        channelList.value = testChannelList
    }
}