package cc.bear3.android.demo.ui.view.smartRefreshLayout.proxy

import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.bear3.android.demo.manager.IDisposable
import cc.bear3.android.demo.manager.http.HttpError
import cc.bear3.android.demo.ui.base.BaseAdapter
import cc.bear3.util.statusadapter.AdapterStatus
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class RefreshProxy<AD : BaseAdapter<T, *>, T>(
    private val layout: SmartRefreshLayout,
    private val adapter: AD,
    private val type: Type = Type.Page
) : IDisposable {

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

    override fun dispose() {

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
        errorType: HttpError,
        dataList: List<T>? = null,
        pageSize: Int = -1
    ) {
        var enableLoadMore = enableLoadMore
        adapter.setErrorStatus(errorType)

        // 数据处理及错误时PageNum归正
        if (errorType === HttpError.Success) {
            if (dataList.isNullOrEmpty()) {
                enableLoadMore = false
                if (isFirstPage()) {
                    adapter.dataRefresh(dataList, true)
                } else {
                    adapter.dataMore(dataList, true)
                }
            } else if (dataList.size < pageSize) {
                if (isFirstPage()) {
                    adapter.dataRefresh(dataList, true)
                } else {
                    adapter.dataMore(dataList, true)
                }
                enableLoadMore = false
            } else {
                if (isFirstPage()) {
                    adapter.dataRefresh(dataList, false)
                } else {
                    adapter.dataMore(dataList, false)
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

    protected open fun initRecyclerView() {
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