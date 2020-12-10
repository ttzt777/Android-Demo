package cc.bear3.android.view.statusbar

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.gyf.immersionbar.ImmersionBar

/**
 *
 * @author TT
 * @since 2020-7-23
 */
class StatusBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var statusBarHeight = 0

    init {
        if (context is Activity) {
            statusBarHeight = ImmersionBar.getStatusBarHeight(context)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(
                statusBarHeight, MeasureSpec.EXACTLY
            )
        )
    }
}