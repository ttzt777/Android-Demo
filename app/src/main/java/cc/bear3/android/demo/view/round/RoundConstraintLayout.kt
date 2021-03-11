package cc.bear3.android.demo.view.round

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.graphics.Path
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import cc.bear3.android.demo.R

/**
 *
 * @author TT
 * @since 2021-3-11
 */
@Suppress("MemberVisibilityCanBePrivate")
@SuppressLint("CustomViewStyleable")
class RoundConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    // 圆角弧度 px
    var radius = 0f
        set(value) {
            field = value
            createPath()
            invalidate()
        }

    // 圆角各个角落的开放标志位
    var leftTopEnable = true
        set(value) {
            field = value
            createPath()
            invalidate()
        }
    var leftBottomEnable = true
        set(value) {
            field = value
            createPath()
            invalidate()
        }
    var rightTopEnable = true
        set(value) {
            field = value
            createPath()
            invalidate()
        }
    var rightBottomEnable = true
        set(value) {
            field = value
            createPath()
            invalidate()
        }

    // 边框宽度
    var strokeWidth = 0f
        set(value) {
            field = value
            strokePaint.strokeWidth = field
            invalidate()
        }

    // 边框颜色
    var strokeColor = 0x00FF_FFFF
        set(value) {
            field = value
            strokePaint.color = field
            invalidate()
        }

    private val path by lazy {
        Path()
    }
    private val strokePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = this@RoundConstraintLayout.strokeWidth
            color = strokeColor
        }
    }
    private val filter by lazy {
        PaintFlagsDrawFilter(
            0,
            Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
        )
    }

    init {
        attrs?.let {
            val array = context.obtainStyledAttributes(attrs, R.styleable.RoundView)

            radius = array.getDimension(R.styleable.RoundView_radius_size, radius)
            leftTopEnable =
                array.getBoolean(R.styleable.RoundView_radius_left_top_enable, leftTopEnable)
            leftBottomEnable =
                array.getBoolean(R.styleable.RoundView_radius_left_bottom_enable, leftBottomEnable)
            rightTopEnable =
                array.getBoolean(R.styleable.RoundView_radius_right_top_enable, rightTopEnable)
            rightBottomEnable =
                array.getBoolean(
                    R.styleable.RoundView_radius_right_bottom_enable,
                    rightBottomEnable
                )
            strokeWidth = array.getDimension(R.styleable.RoundView_stroke_width, strokeWidth)
            strokeColor = array.getColor(R.styleable.RoundView_stroke_color, strokeColor)

            array.recycle()
        }

        //如果你继承的是ViewGroup,注意此行,否则draw方法是不会回调的
        setWillNotDraw(false)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        createPath()
    }

    override fun onDraw(canvas: Canvas) {
        if (radius > 0f && (leftTopEnable or leftBottomEnable or rightTopEnable or rightBottomEnable)) {
            canvas.drawFilter = filter
            canvas.clipPath(path)
        }

        super.onDraw(canvas)

        if (strokeWidth > 0f) {
            canvas.drawPath(path, strokePaint)
        }
    }

    private fun createPath() {
        path.reset()
        val radiusArray = FloatArray(8)
        if (leftTopEnable) {
            radiusArray[0] = radius
            radiusArray[1] = radius
        }
        if (rightTopEnable) {
            radiusArray[2] = radius
            radiusArray[3] = radius
        }
        if (rightBottomEnable) {
            radiusArray[4] = radius
            radiusArray[5] = radius
        }
        if (leftBottomEnable) {
            radiusArray[6] = radius
            radiusArray[7] = radius
        }
        path.addRoundRect(
            paddingStart.toFloat(), paddingTop.toFloat(),
            width - paddingEnd.toFloat(), height - paddingBottom.toFloat(),
            radiusArray, Path.Direction.CW
        )
    }
}