package cc.bear3.android.demo.manager.refresh

import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.bear3.adapter.kernal.AStatusAdapter
import cc.bear3.adapter.kernal.AdapterStatus
import cc.bear3.android.demo.manager.http.HttpError
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * Description: 项目中用到的下拉刷新，上拉加载的帮助类，状态归正及没有更多数据
 * Author: TT
 * From: 2019/3/14
 * Last Version: 1.0.0
 * Last Change Time: 2019/3/14
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/3/14
 * *-*
 */
class RefreshProxy<AD : AStatusAdapter<T, *>, T>(
    val layout: SmartRefreshLayout,
    val adapter: AD,
    private val type: Type = Type.Page,
    private val noMore: Boolean = true
) {

    private var enableRefresh = false
    private var enableLoadMore = false

    var pageNum = 1
        get() {
            return if (field < 1) {
                1
            } else {
                field
            }
        }
    var lastUuid: String? = null
        set(value) {
            field = value
            lastUuidBak = value
        }
        get() {
            return if (field == null) {
                ""
            } else {
                field
            }
        }

    private var pageNumBak = pageNum
    private var lastUuidBak = lastUuid
    private var loadingFlag = false

    init {
        initRecyclerView()
        disableRefresh()
        disableLoadMore()
    }

    fun setOnRefreshListener(listener: OnRefreshListener?) {
        enableRefresh = true
        enableRefresh()
        layout.setOnRefreshListener(listener)
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener?) {
        enableLoadMore = true
        enableLoadMore()
        layout.setOnLoadMoreListener(listener)
    }

    fun setOnRefreshLoadMoreListener(listener: OnRefreshLoadMoreListener?) {
        enableRefresh = true
        enableLoadMore = true
        enableRefresh()
        enableLoadMore()
        layout.setOnRefreshLoadMoreListener(listener)
    }

    fun setLoadFun(
        block: () -> Unit,
        autoLoading: Boolean = true,
        enableRefresh: Boolean = true,
        enableLoadMore: Boolean = true
    ) {
        if (enableRefresh && enableLoadMore) {
            val listener = object : OnRefreshLoadMoreListener {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    onRefresh(block)
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    onLoadMore(block)
                }
            }
            setOnRefreshLoadMoreListener(listener)
        } else if (enableRefresh) {
            setOnRefreshListener { onRefresh(block) }
        } else {
            setOnLoadMoreListener { onLoadMore(block) }
        }

        if (autoLoading) {
            onLoading(block)
        }
    }

    fun isFirstPage(): Boolean {
        return when (type) {
            Type.Page -> pageNum == 1
            Type.Uuid -> TextUtils.isEmpty(lastUuid)
            Type.PageAndUuid -> {
                pageNum == 1 && TextUtils.isEmpty(lastUuid)
            }
        }
    }

    fun autoRefresh(): Boolean {
        if (loadingFlag) {
            return false
        }

        layout.autoRefresh(0, 500, 1.0f, false)

        return true
    }

    private fun initRecyclerView() {
        val count = layout.childCount
        for (index in 0 until count) {
            val recyclerView = layout.getChildAt(index) as? RecyclerView ?: continue
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = adapter
            break
        }
    }

    private fun resetParams() {
        pageNumBak = pageNum
        lastUuidBak = lastUuid
        pageNum = 1
        lastUuid = null
    }

    /**
     * 第一次进入，进行数据加载操作
     */
    fun onLoading(block: () -> Unit) {
        if (loadingFlag) {
            return
        }
        loadingFlag = true
        if (adapter.status == AdapterStatus.Loading) {
            disableRefresh()
            disableLoadMore()
        } else {
            disableLoadMore()
            //            autoRefresh(runnable != null);
        }
        resetParams()
        block()
    }

    /**
     * 针对第一次请求会可能失败的进行第二次请求
     */
    fun onReLoading(block: () -> Unit) {
        loadingFlag = false
        onLoading(block)
    }

    /**
     * 用户进行下拉刷新操作
     */
    fun onRefresh(block: () -> Unit) {
        if (loadingFlag) {
            return
        }
        loadingFlag = true
        resetParams()
        block()
    }

    fun onLoadMore(block: () -> Unit) {
        if (loadingFlag) {
            return
        }
        pageNumBak = pageNum
        pageNum++
        loadingFlag = true
        block()
    }

    @JvmOverloads
    fun onFinish(
        errorType: HttpError = HttpError.Success,
        dataList: List<T>? = null,
        loadAll: Boolean = false
    ) {
        var enableLoadMore = enableLoadMore
        adapter.setErrorStatus(errorType)

        // 数据处理及错误时PageNum归正
        if (errorType === HttpError.Success) {
            if (dataList.isNullOrEmpty() || loadAll) {
                enableLoadMore = false
                if (isFirstPage()) {
                    adapter.dataRefresh(dataList, noMore)
                } else {
                    adapter.dataMore(dataList, noMore)
                }
            } else {
                if (isFirstPage()) {
                    adapter.dataRefresh(dataList)
                } else {
                    adapter.dataMore(dataList)
                }
            }
        } else {
            pageNum = pageNumBak
            lastUuid = lastUuidBak
            if (adapter.status == AdapterStatus.Error) {
                enableLoadMore = false
            }
        }

        // 状态归正
        resetRefreshAndLoadMore()
        if (enableRefresh) {
            enableRefresh()
        } else {
            disableRefresh()
        }
        if (enableLoadMore) {
            enableLoadMore()
        } else {
            disableLoadMore()
        }
        loadingFlag = false
    }

    private fun enableRefresh() {
        layout.setEnableRefresh(true)
    }

    private fun disableRefresh() {
        layout.setEnableRefresh(false)
    }

    private fun enableLoadMore() {
        layout.setEnableLoadMore(true)
    }

    private fun disableLoadMore() {
        layout.setEnableLoadMore(false)
    }

    private fun resetRefreshAndLoadMore() {
        layout.finishRefresh()
        layout.finishLoadMore()
    }

    enum class Type {
        Page,
        Uuid,
        PageAndUuid
    }
}

private fun AStatusAdapter<*, *>.setErrorStatus(error: HttpError) {
    if (status != AdapterStatus.Null && status != AdapterStatus.Loading) {
        return
    }
    status = when (error) {
        HttpError.Success -> AdapterStatus.Content
        HttpError.Server, HttpError.Data -> {
            AdapterStatus.Error
        }
        HttpError.NetWork, HttpError.Timeout, HttpError.Default, HttpError.Permission -> {
            AdapterStatus.Error
        }
    }
}