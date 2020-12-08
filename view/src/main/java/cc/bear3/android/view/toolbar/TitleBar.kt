package cc.bear3.android.view.toolbar

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import cc.bear3.android.view.R
import cc.bear3.android.view.databinding.ViewTitleBarBinding

/**
 * Description:
 * Author: TT
 */
@Suppress("MemberVisibilityCanBePrivate")
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTitleBarBinding

    var hasIcon = true
        set(value) {
            field = value
            binding.back.visibility = if (value) View.VISIBLE else View.GONE
        }

    var iconResId: Int = R.mipmap.ic_arrow_left_bold
        set(value) {
            field = value
            binding.back.setImageResource(iconResId)
        }

    var title = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var hasDivide = false
        set(value) {
            field = value
            binding.divide.visibility = if (value) View.VISIBLE else View.GONE
        }

    init {
        val view = View.inflate(context, R.layout.view_title_bar, this)
        binding = ViewTitleBarBinding.bind(view)

        attrs?.let {
            val array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)

            hasIcon = array.getBoolean(R.styleable.TitleBar_tb_has_icon, hasIcon)
            iconResId = array.getResourceId(R.styleable.TitleBar_tb_icon_res, iconResId)
            title = array.getString(R.styleable.TitleBar_tb_title) ?: ""
            hasDivide = array.getBoolean(R.styleable.TitleBar_tb_has_divide, hasDivide)

            array.recycle()
        }

        binding.back.visibility = if (hasIcon) View.VISIBLE else View.GONE
        binding.back.setImageResource(iconResId)
        binding.title.text = title
        binding.divide.visibility = if (hasDivide) View.VISIBLE else View.GONE

        setIconOnclickListener(OnClickListener {
            (context as? Activity)?.onBackPressed()
        })
    }

    fun setIconOnclickListener(listener: OnClickListener) {
        binding.back.setOnClickListener(listener)
    }
}