package cc.bear3.android.demo.view.button

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import cc.bear3.android.demo.R

/**
 *
 * @author TT
 * @since 2021-3-2
 */
@Suppress("MemberVisibilityCanBePrivate")
class ConfirmButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    private val enableBg by lazy { GradientDrawable() }
    private val disableBg by lazy { GradientDrawable() }

    private var enableSolidColor = ContextCompat.getColor(context, R.color.app_confirm_solid_enable)
    private var disableSolidColor =
        ContextCompat.getColor(context, R.color.app_confirm_solid_disable)
    private var enableStrokeColor =
        ContextCompat.getColor(context, R.color.app_confirm_stroke_enable)
    private var disableStrokeColor =
        ContextCompat.getColor(context, R.color.app_confirm_stroke_disable)
    private var enableTextColor = ContextCompat.getColor(context, R.color.app_confirm_text_enable)
    private var disableTextColor = ContextCompat.getColor(context, R.color.app_confirm_text_disable)

    // 圆角大小
    var cornerRadius = 0f
        set(value) {
            field = value
            requestLayout()
        }

    // 圆角是否为高度的一半
    var isHalfHeightCorner = false
        set(value) {
            field = value
            requestLayout()
        }

    // 背景描边宽度
    var strokeWidth = 0f
        set(value) {
            field = value
            requestLayout()
        }

    init {
        attrs?.let {
            val array = context.obtainStyledAttributes(it, R.styleable.ConfirmButton)

            val count = array.indexCount
            for (index in 0 until count) {
                when (val indexValue = array.getIndex(index)) {
                    R.styleable.ConfirmButton_cb_enable_solid_color -> {
                        enableSolidColor = array.getColor(indexValue, enableSolidColor)
                    }
                    R.styleable.ConfirmButton_cb_disable_solid_color -> {
                        disableSolidColor = array.getColor(indexValue, disableSolidColor)
                    }
                    R.styleable.ConfirmButton_cb_enable_stroke_color -> {
                        enableStrokeColor = array.getColor(indexValue, enableStrokeColor)
                    }
                    R.styleable.ConfirmButton_cb_disable_stroke_color -> {
                        disableStrokeColor = array.getColor(indexValue, disableStrokeColor)
                    }
                    R.styleable.ConfirmButton_cb_enable_text_color -> {
                        enableTextColor = array.getColor(indexValue, enableTextColor)
                    }
                    R.styleable.ConfirmButton_cb_disable_text_color -> {
                        disableTextColor = array.getColor(indexValue, disableTextColor)
                    }
                    R.styleable.ConfirmButton_cb_is_half_height_corner -> {
                        isHalfHeightCorner = array.getBoolean(indexValue, isHalfHeightCorner)
                    }
                    R.styleable.ConfirmButton_cb_corner_radius -> {
                        cornerRadius = array.getDimension(indexValue, cornerRadius)
                    }
                    R.styleable.ConfirmButton_cb_stroke_width -> {
                        strokeWidth = array.getDimension(indexValue, strokeWidth)
                    }
                }
            }

            array.recycle()
        }

        // 这里不是瞎写的 需要走一遍初始值，不然字体颜色不对
        isEnabled = isEnabled
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (isHalfHeightCorner) {
            cornerRadius = height / 2f
        } else {
            if (cornerRadius > height / 2f) {
                cornerRadius = height / 2f
            }
        }

        setBgSelector()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        setTextColor(if (enabled) enableTextColor else disableTextColor)
    }

    private fun setBgSelector() {
        val bg = StateListDrawable()

        setDrawable(enableBg, enableSolidColor, enableStrokeColor)
        setDrawable(disableBg, disableSolidColor, disableStrokeColor)

        bg.addState(intArrayOf(-android.R.attr.state_enabled), disableBg)
        bg.addState(intArrayOf(android.R.attr.state_enabled), enableBg)

        background = bg
    }

    private fun setDrawable(gd: GradientDrawable, solidColor: Int, strokeColor: Int) {
        with(gd) {
            cornerRadius = this@ConfirmButton.cornerRadius
            setColor(solidColor)
            setStroke(strokeWidth.toInt(), strokeColor)
        }
    }
}