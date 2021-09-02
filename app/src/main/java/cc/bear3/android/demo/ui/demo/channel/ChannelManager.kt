package cc.bear3.android.demo.ui.demo.channel

import androidx.lifecycle.MutableLiveData

/**
 *
 * @author TT
 * @since 2021-3-11
 */
object ChannelManager {
    val currentChannel = MutableLiveData<ChannelData?>()
    val channelList = MutableLiveData<List<ChannelData>>()

    private val testChannelList = getAllChannelList()

    init {
        val originChannelList = testChannelList.subList(0, 5)
        channelList.value = originChannelList
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

    fun requestSuggestChannelList(): List<ChannelData> {
        val targetChannelList = mutableListOf<ChannelData>().apply {
            addAll(testChannelList)
        }
        val myChannelList = channelList.value
        if (myChannelList?.isNullOrEmpty() == false) {
            for (temp in myChannelList) {
                targetChannelList.remove(temp)
            }
        }

        return targetChannelList
    }

    private fun saveChannelListToLocal() {

    }

    private fun getAllChannelList(): List<ChannelData> {
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