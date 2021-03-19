package cc.bear3.android.demo.ui.base

import android.os.Bundle
import android.view.View
import cc.bear3.android.demo.R
import cc.bear3.android.demo.ui.base.lec.LecActivity
import cc.bear3.android.demo.ui.base.lec.LecState
import com.gyf.immersionbar.ImmersionBar
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2020-12-4
 */
abstract class BaseActivity : LecActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("Activity onCreate: %s", getTagName())

        setImmersionBar()

        initView(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        Timber.d("Activity onStart: %s", getTagName())
    }

    override fun onResume() {
        super.onResume()

        Timber.d("Activity onResume: %s", getTagName())
    }

    override fun onPause() {
        super.onPause()

        Timber.d("Activity onPause: %s", getTagName())
    }

    override fun onStop() {
        super.onStop()

        Timber.d("Activity onStop: %s", getTagName())
    }

    override fun onDestroy() {
        super.onDestroy()

        Timber.d("Activity onDestroy: %s", getTagName())
    }

    protected abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreateLoadingView(): View {
        return layoutInflater.inflate(R.layout.layout_loading, root, false)
    }

    override fun onCreateErrorView(): View {
        return layoutInflater.inflate(R.layout.layout_error, root, false)
    }

    override fun getTopMargin(lecState: LecState): Int {
        return ImmersionBar.getStatusBarHeight(this) + resources.getDimensionPixelSize(R.dimen.title_bar_height)
    }

    protected open fun setImmersionBar() {
        ImmersionBar.with(this)
            .statusBarColor(R.color.transparent)
            .statusBarDarkFont(true)
            .init()
    }

    protected open fun getTagName() : String {
        return javaClass.simpleName
    }

    protected fun initParams(savedInstanceState: Bundle?, block: (bundle: Bundle) -> Unit){
        val bundle = savedInstanceState
            ?: if (intent.extras != null) {
                intent.extras
            } else {
                null
            }

        bundle?.let {
            block(it)
        }
    }
}