package cc.bear3.android.demo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import cc.bear3.android.demo.R
import cc.bear3.lec.LecFragment
import cc.bear3.lec.LecState
import com.gyf.immersionbar.ImmersionBar
import timber.log.Timber
import java.lang.reflect.ParameterizedType

/**
 *
 * @author TT
 * @since 2020-12-4
 */
abstract class BaseFragment<VB : ViewBinding>() :
    LecFragment() {
    protected lateinit var binding: VB

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
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = method.invoke(null, layoutInflater, root, false) as VB
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
}