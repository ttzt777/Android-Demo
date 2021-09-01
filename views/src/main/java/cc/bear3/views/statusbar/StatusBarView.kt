package cc.bear3.views.statusbar

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View

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

    init {
        if (statusBarHeight == 0) {
            statusBarHeight = getStatusBarHeight(context)
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

    companion object {
        private const val STATUS_BAR_HEIGHT = "status_bar_height"
        private var statusBarHeight = 0

        fun getStatusBarHeight(context: Context): Int {
            if (statusBarHeight <= 0) {
                try {
                    val resourceId =
                        Resources.getSystem().getIdentifier(STATUS_BAR_HEIGHT, "dimen", "android")
                    if (resourceId > 0) {
                        val sizeOne = context.resources.getDimensionPixelSize(resourceId)
                        val sizeTwo = Resources.getSystem().getDimensionPixelSize(resourceId)
                        statusBarHeight = if (sizeTwo >= sizeOne) {
                            sizeTwo
                        } else {
                            val densityOne = context.resources.displayMetrics.density
                            val densityTwo = Resources.getSystem().displayMetrics.density
                            val f = sizeOne * densityTwo / densityOne
                            (if (f >= 0) f + 0.5f else f - 0.5f).toInt()
                        }
                    }
                } catch (ignored: Resources.NotFoundException) {
                    statusBarHeight = 0
                }
            }
            return statusBarHeight
        }
    }
}