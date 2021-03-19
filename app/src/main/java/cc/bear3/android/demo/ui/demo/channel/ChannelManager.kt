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
        val testChannelList = getAllChannelList().subList(0, 5)
        channelList.value = testChannelList
    }

    fun observeChannelList(owner: LifecycleOwner, onChanged: (List<ChannelData>) -> Unit) {
        val wrappedObserver = Observer<List<ChannelData>> { t -> onChanged.invoke(t) }
        channelList.observe(owner, wrappedObserver)
    }

    fun removeObserveChannelList(onChanged: (List<ChannelData>) -> Unit) {
        channelList.removeObserver(onChanged)
    }

    /**
     * 从本地拉取
     */
    fun getMyChannelListFromLocal() {

    }

    /**
     * 从服务器拉取
     */
    fun getMyChannelListFromServer() {

    }

    fun requestSuggestChannelList() : List<ChannelData> {
        val testChannelList = mutableListOf<ChannelData>().apply {
            addAll(getAllChannelList())
        }
        val myChannelList = channelList.value
        if (myChannelList?.isNullOrEmpty() == true) {
            for (temp in myChannelList) {
                testChannelList.remove(temp)
            }
        }

        return testChannelList
    }

    private fun saveChannelListToLocal() {

    }

    private fun getAllChannelList() : List<ChannelData> {
        return listOf(
            ChannelData(0, "推荐"),
            ChannelData(1, "我的最爱"),
            ChannelData(2, "哈哈哈哈"),
            ChannelData(3, "本地频道"),
            ChannelData(4, "我是名字超级长的频道"),
            ChannelData(5, "寒江孤影"),
            ChannelData(6, "江湖故人"),
            ChannelData(7, "相逢何必曾相识"),
            ChannelData(8, "Android"),
            ChannelData(9, "AS"),
            ChannelData(10, "Bear3"),
            ChannelData(11, "Java"),
            ChannelData(12, "TextView"),
            ChannelData(13, "Button"),
            ChannelData(14, "Preface"),
            ChannelData(15, "司藤"),
            ChannelData(16, "ViewGroup")
        )
    }
}