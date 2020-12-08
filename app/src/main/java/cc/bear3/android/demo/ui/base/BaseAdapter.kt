package cc.bear3.android.demo.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.R
import cc.bear3.android.demo.manager.http.HttpError
import cc.bear3.util.statusadapter.*

/**
 * Description:
 * Author: TT
 */
abstract class BaseAdapter<T, VH : AContentViewHolder> :
    AStatusAdapter<T, VH>() {

    override fun getInitStatus(): AdapterStatus {
        return AdapterStatus.Null
    }

    override fun onCreateEmptyViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): AEmptyViewHolder {
        return EmptyViewHolder(getView(parent, R.layout.adapter_empty))
    }

    override fun onCreateLErrorViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): AErrorViewHolder {
        return ErrorViewHolder(getView(parent, R.layout.adapter_empty))
    }

    override fun onCreateLoadingViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ALoadingViewHolder {
        return LoadingViewHolder(getView(parent, R.layout.adapter_loading))
    }

    override fun onCreateNoMoreViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ANoMoreViewHolder {
        return NoMoreViewHolder(getView(parent, R.layout.adapter_no_more))
    }

    override fun onBindEmptyViewHolder(holder: AEmptyViewHolder) {

    }

    override fun onBindErrorViewHolder(holder: AErrorViewHolder) {

    }

    override fun onBindLoadingViewHolder(holder: ALoadingViewHolder) {

    }

    override fun onBindNoMoreViewHolder(holder: ANoMoreViewHolder) {

    }

    open fun setErrorStatus(error: HttpError) {
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

    open class EmptyViewHolder(view: View) : AEmptyViewHolder(view) {

    }

    open class LoadingViewHolder(view: View) : ALoadingViewHolder(view) {

    }

    open class ErrorViewHolder(view: View) : AErrorViewHolder(view) {

    }

    open class NoMoreViewHolder(view: View) : ANoMoreViewHolder(view) {

    }

    open class ContentViewHolder(parent: ViewGroup, resId: Int) :AContentViewHolder(getView(parent, resId))
}

internal fun getView(parent: ViewGroup, resId: Int): View {
    return LayoutInflater.from(parent.context).inflate(resId, parent, false)
}