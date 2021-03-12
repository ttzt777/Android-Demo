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

        Timber.d("Fragment onCreate: %s", getFragmentName())
    }

    override fun onStart() {
        super.onStart()

        Timber.d("Fragment onStart: %s", getFragmentName())
    }

    override fun onResume() {
        super.onResume()

        Timber.d("Fragment onResume: %s", getFragmentName())
    }

    override fun onPause() {
        super.onPause()

        Timber.d("Fragment onPause: %s", getFragmentName())
    }

    override fun onStop() {
        super.onStop()

        Timber.d("Fragment onStop: %s", getFragmentName())
    }

    override fun onDestroy() {
        super.onDestroy()

        Timber.d("Fragment onDestroy: %s", getFragmentName())
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

    protected fun getFragmentName() : String {
        return javaClass.simpleName
    }
}