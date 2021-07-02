package cc.bear3.android.demo.ui.view.smartRefreshLayout

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import cc.bear3.android.demo.databinding.ItemSmartRefreshLayoutBinding
import cc.bear3.android.demo.databinding.PageSmartRefreshLayoutBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.base.SingleFastAdapter
import cc.bear3.android.demo.ui.view.smartRefreshLayout.proxy.RefreshProxy
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
    private val adapter = object : SingleFastAdapter<String, ItemSmartRefreshLayoutBinding>(ItemSmartRefreshLayoutBinding::inflate) {
        override fun convert(binding: ItemSmartRefreshLayoutBinding, data: String) {

        }
    }

    private val refreshProxy by lazy {
        RefreshProxy<SingleFastAdapter<String, ItemSmartRefreshLayoutBinding>, String>(binding.smartRefreshLayout, adapter)
    }

    private

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        val loadFun: () -> Unit = {
            presenter?.getNewsList(refreshProxy?.pageNum ?: 0)
        }
    }

    private class ViewModel {
        val dataList = MutableLiveData<MutableList<String>>().apply {
            value = mutableListOf()
        }

        fun requestData(page: Int, pageSize: Int = 20) {
            Observable.create(ObservableOnSubscribe<List<String>> { emitter ->
                Thread.sleep(3000)
                val offset = page * pageSize;
                val result = mutableListOf<String>()
                for (index in 0..pageSize) {
                    result.add((offset + index + 1).toString())
                }
                emitter.onNext(result)

            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<List<String>> {
                    override fun onSubscribe(d: Disposable) {
                        
                    }

                    override fun onNext(t: List<String>) {
                        val originList = dataList.value!!
                        originList.addAll(t)
                        dataList.value = originList
                    }

                    override fun onError(e: Throwable) {
                        
                    }

                    override fun onComplete() {
                        
                    }
                })
        }
    }
}