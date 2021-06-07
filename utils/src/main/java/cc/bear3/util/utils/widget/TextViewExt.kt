package cc.bear3.util.utils.widget

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView

/**
 *
 * @author TT
 * @since 2021-3-1
 */

/**
 * 用于TextView中设置带单位且单位与正常文字不一样大的情况
 * 如果需要空格，自行添加到origin或者target中
 */
fun TextView.setText(origin: String, target: String, targetSp: Int) {
    val builder = SpannableStringBuilder(origin)
    val sp = SpannableString(target)
    sp.setSpan(AbsoluteSizeSpan(targetSp, true), 0, sp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    builder.append(sp)
    text = builder
}