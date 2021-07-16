package cc.bear3.android.demo.ui.base.lec

import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe

/**
 *
 * @author TT
 * @since 2021-6-2
 */
interface ILecPage {
    val params : FrameLayout.LayoutParams

    val state: MutableLiveData<LecState>

    var root: FrameLayout

    var loadingView: View?
    var errorView: View?

    fun getLifecycleOwner(): LifecycleOwner

    fun observeLecState(onChanged: (LecState) -> Unit) {
        val wrappedObserver = Observer<LecState> { t -> onChanged.invoke(t) }
        state.observe(getLifecycleOwner(), wrappedObserver)
    }

    fun removeObserveLecState() {
        state.removeObservers(getLifecycleOwner())
    }

    fun defaultObserverLecState() {
        observeLecState {
            when (it) {
                LecState.Loading -> showLoadingLayout()
                LecState.Error -> showErrorLayout()
                LecState.Content -> showContentLayout()
            }
        }
    }

    fun changeLecState(lecState: LecState) {
        state.value = lecState
    }

    fun showLoadingLayout() {
        dismissErrorLayout()

        val topMargin = getTopMargin(state.value!!)

        if (loadingView == null) {
            loadingView = onCreateLoadingView()

            params.topMargin = topMargin
            root.addView(loadingView, params)
        } else {
            loadingView?.let {
                it.layoutParams = (it.layoutParams as FrameLayout.LayoutParams).apply {
                    this.topMargin = topMargin
                }
            }
        }

        loadingView?.let {
            onLoadingViewCreated(it)
        }
    }

    fun dismissLoadingLayout() {
        loadingView?.let {
            root.removeView(it)
        }
    }

    fun showErrorLayout() {
        dismissLoadingLayout()

        val topMargin = getTopMargin(state.value!!)

        if (errorView == null) {
            errorView = onCreateErrorView()

            params.topMargin = topMargin
            root.addView(errorView, params)
        } else {
            errorView?.let {
                it.layoutParams = (it.layoutParams as FrameLayout.LayoutParams).apply {
                    this.topMargin = topMargin
                }
            }
        }

        errorView?.let {
            onErrorViewCreated(it)
        }
    }

    fun dismissErrorLayout() {
        errorView?.let {
            root.removeView(it)
        }
    }

    fun showContentLayout() {
        dismissLoadingLayout()
        dismissErrorLayout()
    }

    fun onCreateContentView(): View

    fun onCreateLoadingView(): View

    fun onCreateErrorView(): View

    fun onLoadingViewCreated(loadingView: View) {

    }

    fun onErrorViewCreated(errorView: View) {

    }

    fun getTopMargin(lecState: LecState): Int {
        return 0
    }
}