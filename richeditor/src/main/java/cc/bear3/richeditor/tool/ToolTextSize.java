package cc.bear3.richeditor.tool;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

/**
 * 文字大小
 */
public class ToolTextSize extends ToolItem {
    private int textSize = 16;

    @Override
    public void attach(EditText editText) {
        float fontScale = editText.getResources().getDisplayMetrics().scaledDensity;
        textSize = (int) (editText.getTextSize() / fontScale + 0.5f);

        super.attach(editText);
    }

    @Override
    public void applyStyle(int start, int end) {
        int start_fact = start;
        int end_fact = end;
        Editable s = editText.getEditableText();

        AbsoluteSizeSpan[] styleSpans = s.getSpans(start - 1, end + 1, AbsoluteSizeSpan.class);

        for (AbsoluteSizeSpan styleSpan : styleSpans) {
            int spanStart = s.getSpanStart(styleSpan);
            int spanEnd = s.getSpanEnd(styleSpan);
            if (styleSpan.getSize() == textSize) {
                if (spanStart < start) {
                    start_fact = spanStart;
                }
                if (spanEnd > end) {
                    end_fact = spanEnd;
                }
                if (spanStart != spanEnd) {
                    if (spanStart <= start && spanEnd >= end) {
                        return;
                    } else {
                        s.removeSpan(styleSpan);
                    }
                }
            } else {   //非当前字体大小
                if (spanEnd <= start || spanStart >= end) {   //不在选中区间内

                } else {      //在选中区间内
                    s.removeSpan(styleSpan);   //移除此style

                    if (spanStart < start) {
                        //恢复
                        s.setSpan(new AbsoluteSizeSpan(styleSpan.getSize(), true), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (spanEnd > end) {
                        //恢复
                        s.setSpan(new AbsoluteSizeSpan(styleSpan.getSize(), true), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }

        s.setSpan(new AbsoluteSizeSpan(textSize, true), start_fact, end_fact, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        if (editText == null) {
            return;
        }

        Editable s = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            AbsoluteSizeSpan[] styleSpans = s.getSpans(selStart - 1, selStart, AbsoluteSizeSpan.class);
            for (AbsoluteSizeSpan styleSpan : styleSpans) {
                if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                    textSize = styleSpan.getSize();
                }
            }
        } else if (selStart != selEnd) {
            AbsoluteSizeSpan[] styleSpans = s.getSpans(selStart, selEnd, AbsoluteSizeSpan.class);
            for (AbsoluteSizeSpan styleSpan : styleSpans) {

                if (s.getSpanStart(styleSpan) <= selStart
                        && s.getSpanEnd(styleSpan) >= selEnd) {
                    if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                        textSize = styleSpan.getSize();
                    }
                }
            }
        }
        notifyStateChanged();
    }

    @Override
    protected void onStateChanged() {
        if (editText == null) {
            return;
        }

        int selStart = editText.getSelectionStart();
        int selEnd = editText.getSelectionEnd();
        if (selStart < selEnd) {
            applyStyle(selStart, selEnd);
        }
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        notifyStateChanged();
        onStateChanged();
    }
}
