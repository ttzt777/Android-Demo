package cc.bear3.android.demo.ui.base

import android.os.Bundle
import android.view.View
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
abstract class BaseFragment : LecFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Fragment onCreate: %s", javaClass.simpleName)
    }

    override fun onCreateLoadingView(): View {
        return layoutInflater.inflate(R.layout.fragment_loading, root, false)
    }

    override fun onCreateErrorView(): View {
        return layoutInflater.inflate(R.layout.fragment_error, root, false)
    }

    override fun getTopMargin(lecState: LecState): Int {
        return ImmersionBar.getStatusBarHeight(this) + resources.getDimensionPixelSize(R.dimen.title_bar_height)
    }

    protected fun getAvailableArgs(savedInstanceState: Bundle?, block: (bundle: Bundle) -> Unit){
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
}