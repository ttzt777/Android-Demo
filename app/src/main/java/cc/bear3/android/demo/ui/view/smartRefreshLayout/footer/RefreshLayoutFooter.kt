package cc.bear3.android.demo.ui.view.smartRefreshLayout.footer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ViewRefreshLayoutFooterBinding
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-6-21
 */
class RefreshLayoutFooter(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), RefreshFooter {

    private val binding: ViewRefreshLayoutFooterBinding

    init {
        val view = inflate(context, R.layout.view_refresh_layout_footer, this)
        binding = ViewRefreshLayoutFooterBinding.bind(view)
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {

    }

    override fun getView(): View {
        return this
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }

    override fun setPrimaryColors(vararg colors: Int) {

    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
        Timber.d("onInitialized - height is $height, maxDragHeight is $maxDragHeight")
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
        Timber.d("onMoving - isDragging is $isDragging, percent is $percent, offset is $offset,  height is $height, maxDragHeight is $maxDragHeight")
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        Timber.d("onReleased - height is $height, maxDragHeight is $maxDragHeight")
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        Timber.d("onStartAnimator - height is $height, maxDragHeight is $maxDragHeight")
        with(binding.loading) {
            if (!isAnimating) {
                playAnimation()
            }
        }
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        Timber.d("onFinish - success is $success")
        binding.loading.cancelAnimation()
        return 0
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
        Timber.d("onHorizontalDrag - percentX is $percentX, offsetX is $offsetX, offsetMax is $offsetMax")
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        return false
    }
}