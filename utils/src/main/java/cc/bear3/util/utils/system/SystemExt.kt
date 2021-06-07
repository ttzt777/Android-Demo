package cc.bear3.util.utils.system

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import cc.bear3.util.utils.R

/**
 *
 * @author TT
 * @since 2021-3-1
 */

fun toCopyBoard(
        context: Context,
        sequence: CharSequence?,
        successTip: String = context.getString(R.string.app_copy_success),
        failedTip: String = context.getString(R.string.app_copy_failed)
) {
    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    when {
        sequence.isNullOrEmpty() -> {
            ToastManager.show(context, failedTip)
        }
        cm == null -> {
            ToastManager.show(context, failedTip)
        }
        else -> {
            // 将文本内容放到系统剪贴板里。
            cm.setPrimaryClip(ClipData.newPlainText(null, sequence))
            ToastManager.show(context, successTip)
        }
    }
}