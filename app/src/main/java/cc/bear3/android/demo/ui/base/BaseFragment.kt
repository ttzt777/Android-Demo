package cc.bear3.android.demo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import cc.bear3.android.demo.R
import cc.bear3.android.demo.ui.base.lec.LecFragment
import cc.bear3.android.demo.ui.base.lec.LecState
import com.gyf.immersionbar.ImmersionBar
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2020-12-4
 */
abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) :
    LecFragment() {
    private lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Fragment onCreate: %s", getTagName())
    }

    override fun onStart() {
        super.onStart()

        Timber.d("Fragment onStart: %s", getTagName())
    }

    override fun onResume() {
        super.onResume()

        Timber.d("Fragment onResume: %s", getTagName())
    }

    override fun onPause() {
        super.onPause()

        Timber.d("Fragment onPause: %s", getTagName())
    }

    override fun onStop() {
        super.onStop()

        Timber.d("Fragment onStop: %s", getTagName())
    }

    override fun onDestroy() {
        super.onDestroy()

        Timber.d("Fragment onDestroy: %s", getTagName())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(savedInstanceState)
    }

    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreateContentView(): View {
        binding = inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateLoadingView(): View {
        return layoutInflater.inflate(R.layout.layout_loading, root, false)
    }

    override fun onCreateErrorView(): View {
        return layoutInflater.inflate(R.layout.layout_error, root, false)
    }

    override fun getTopMargin(lecState: LecState): Int {
        return ImmersionBar.getStatusBarHeight(this) + resources.getDimensionPixelSize(R.dimen.title_bar_height)
    }

    protected fun initParams(savedInstanceState: Bundle?, block: (bundle: Bundle) -> Unit) {
        val bundle = savedInstanceState
            ?: if (arguments != null) {
                arguments
            } else {
                null
            }

        bundle?.let {
            block(it)
        }
    }

    protected open fun getTagName(): String {
        return javaClass.simpleName
    }

    protected fun useBinding(block: (binding: VB) -> Unit) {
        if (this::binding.isInitialized) {
            block(binding)
        }
    }
}