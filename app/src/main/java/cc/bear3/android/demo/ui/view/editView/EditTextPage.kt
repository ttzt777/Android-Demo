package cc.bear3.android.demo.ui.view.editView

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.PageEdittextBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim
import cc.bear3.richeditor.action.ActionBold
import cc.bear3.util.utils.view.onClick

/**
 *
 * @author TT
 * @since 2020-12-9
 */
class EditTextPage : BaseActivity<PageEdittextBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        val actionBold = ActionBold().apply {
            setActionCallback {
                binding.actionBold.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@EditTextPage,
                        if (it.flag) {
                            R.color.color_accent
                        } else {
                            R.color.text_summary
                        }
                    )
                )
            }
        }
        binding.editor.addActionItem(actionBold)
        binding.actionBold.onClick {
            actionBold.toggleFlag()
        }
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, EditTextPage::class.java)
            context.startWithAnim(intent)
        }
    }
}