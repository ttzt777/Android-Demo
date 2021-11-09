package cc.bear3.android.demo.ui.view.editView

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.PageEdittextBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim
import cc.bear3.richeditor.helper.HelperBlockImage
import cc.bear3.richeditor.tool.*
import cc.bear3.util.utils.view.onClick

/**
 *
 * @author TT
 * @since 2020-12-9
 */
class EditTextPage : BaseActivity<PageEdittextBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        with(binding) {
            val listenerImpl = IToolItemCallback {
                when (it) {
                    is ToolBold -> actionBold.imageTintList = getTintList(it.flag)
                    is ToolItalic -> actionItalic.imageTintList = getTintList(it.flag)
                    is ToolUnderline -> actionUnderline.imageTintList = getTintList(it.flag)
                    is ToolStrikethrough -> actionStrikethrough.imageTintList = getTintList(it.flag)
                    is ToolTextSize -> {
                        actionSizeSmall.setTextColor(getTintList(false))
                        actionSizeMid.setTextColor(getTintList(false))
                        actionSizeBig.setTextColor(getTintList(false))
                        when (it.textSize) {
                            13 -> actionSizeSmall.setTextColor(getTintList(true))
                            16 -> actionSizeMid.setTextColor(getTintList(true))
                            20 -> actionSizeBig.setTextColor(getTintList(true))
                        }
                    }
                }
            }

            fun <T : ToolItem> T.withListener(): T {
                this.setToolStateCallback(listenerImpl)
                return this
            }

            val toolBold = ToolBold().withListener()
            val toolItalic = ToolItalic().withListener()
            val toolUnderline = ToolUnderline().withListener()
            val toolStrikethrough = ToolStrikethrough().withListener()
            val toolTextSize = ToolTextSize().withListener()

            editor.addToolItems(
                toolBold, toolItalic, toolUnderline, toolStrikethrough, toolTextSize
            )

            val helperImage = HelperBlockImage()

            editor.addHelperItems(helperImage)

            actionBold.setOnClickListener() {
                toolBold.toggleFlag()
            }
            actionItalic.setOnClickListener() {
                toolItalic.toggleFlag()
            }
            actionUnderline.setOnClickListener() {
                toolUnderline.toggleFlag()
            }
            actionStrikethrough.setOnClickListener() {
                toolStrikethrough.toggleFlag()
            }
            actionSizeSmall.setOnClickListener() {
                toolTextSize.textSize = 13
            }
            actionSizeMid.setOnClickListener() {
                toolTextSize.textSize = 16
            }
            actionSizeBig.setOnClickListener() {
                toolTextSize.textSize = 20
            }
            actionImage.onClick {
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.image_3)
                helperImage.insertImage("0", BitmapDrawable(resources, bitmap))

                var progress = 0

                val runnable = {
                    helperImage.changeProgress("0", ++progress)
                }
                val handler = Handler(Looper.getMainLooper())
                repeat(100) {
                    handler.postDelayed(runnable, (it + 1) * 200L)
                }
            }
        }
    }

    private fun getTintList(flag: Boolean): ColorStateList {
        return ColorStateList.valueOf(
            ContextCompat.getColor(
                this@EditTextPage,
                if (flag) {
                    R.color.color_accent
                } else {
                    R.color.text_summary
                }
            )
        )
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, EditTextPage::class.java)
            context.startWithAnim(intent)
        }
    }
}