package cc.bear3.android.demo.ui.view.smartRefreshLayout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import cc.bear3.android.demo.databinding.ItemSmartRefreshLayoutBinding
import cc.bear3.android.demo.databinding.PageSmartRefreshLayoutBinding
import cc.bear3.android.demo.manager.http.HttpError
import cc.bear3.android.demo.manager.refresh.RefreshProxy
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.adapter.fast.SingleFastAdapter
import cc.bear3.android.demo.util.collection.size
import cc.bear3.android.demo.util.context.startWithAnim
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author TT
 * @since 2021-6-21
 */
class SmartRefreshLayoutPage :
    BaseActivity<PageSmartRefreshLayoutBinding>(PageSmartRefreshLayoutBinding::inflate) {
    private val adapter = object :
        SingleFastAdapter<String, ItemSmartRefreshLayoutBinding>(ItemSmartRefreshLayoutBinding::inflate) {
        override fun convert(binding: ItemSmartRefreshLayoutBinding, data: String) {
            binding.title.text = data
        }
    }

    private val viewModel = ViewModel()

    private val refreshProxy by lazy {
        RefreshProxy<SingleFastAdapter<String, ItemSmartRefreshLayoutBinding>, String>(
            binding.smartRefreshLayout,
            adapter
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
        val loadFun: () -> Unit = {
            viewModel.requestData(refreshProxy.pageNum)
        }

        viewModel.dataList.observe(this) { result ->
            result?.let {
                refreshProxy.onFinish(HttpError.Success, it, it.size() < ViewModel.PAGE_SIZE)
            }
        }
        refreshProxy.setLoadFun(loadFun)
    }

    companion object {
        fun invoke(context: Context) {
            context.startWithAnim(Intent(context, SmartRefreshLayoutPage::class.java))
        }
    }

    private class ViewModel {
        val dataList = MutableLiveData<MutableList<String>>().apply {
            value = null
        }

        fun requestData(page: Int, pageSize: Int = PAGE_SIZE) {
            Observable.create(ObservableOnSubscribe<List<String>> { emitter ->
                Thread.sleep(3000)
                val offset = (page - 1) * pageSize
                val result = mutableListOf<String>()
                if (page <= 3) {
                    for (index in 0 until pageSize) {
                        result.add((offset + index + 1).toString())
                    }
                }
                emitter.onNext(result)

            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<String>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: List<String>) {
                        dataList.value = t.toMutableList()
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        }

        companion object {
            const val PAGE_SIZE = 20
        }
    }
}