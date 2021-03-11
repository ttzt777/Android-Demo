package cc.bear3.android.demo.ui.view.editView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.ViewLoginEditBinding

/**
 * 常用登录输入框
 * @author TT
 * @since 2020-12-22
 */
class LoginEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), TextWatcher {

    private var binding: ViewLoginEditBinding

    var type = Type.Undefine
        set(value) {
            field = value

            with(binding) {
                edit.isEnabled = true
                clear.visibility = View.GONE
                status.visibility = View.GONE
                codeText.visibility = View.GONE
                codeTimer.visibility = View.GONE
            }
        }

    init {
        val view = View.inflate(context, R.layout.view_login_edit, this)
        binding = ViewLoginEditBinding.bind(view)
    }

    override fun afterTextChanged(s: Editable?) {
        TODO("Not yet implemented")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("Not yet implemented")
    }

    enum class Type {
        Undefine,
        Password,
        Code,
        Choose
    }
}