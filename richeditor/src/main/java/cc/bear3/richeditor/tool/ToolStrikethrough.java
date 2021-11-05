package cc.bear3.richeditor.tool;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

/**
 * 删除线
 */
public class ToolStrikethrough extends ToolFlagItem{
    @Override
    protected void setStyle(int start, int end) {
        int start_fact = start;
        int end_fact = end;
        Editable s = editText.getEditableText();
        StrikethroughSpan[] styleSpans = s.getSpans(start - 1, end + 1, StrikethroughSpan.class);
        for (StrikethroughSpan styleSpan : styleSpans) {

            int spanStart = s.getSpanStart(styleSpan);
            int spanEnd = s.getSpanEnd(styleSpan);
            if (spanStart != spanEnd) {
                if (spanStart < start) {
                    start_fact = spanStart;
                }
                if (spanEnd > end) {
                    end_fact = spanEnd;
                }

                if (spanStart <= start && spanEnd >= end) {
                    return;
                } else {
                    s.removeSpan(styleSpan);
                }
            }

        }
        s.setSpan(new StrikethroughSpan(), start_fact, end_fact, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    protected void removeStyle(int start, int end) {
        Editable s = editText.getEditableText();
        StrikethroughSpan[] styleSpans = s.getSpans(start, end, StrikethroughSpan.class);
        for (StrikethroughSpan styleSpan : styleSpans) {

            int spanStart = s.getSpanStart(styleSpan);
            int spanEnd = s.getSpanEnd(styleSpan);
            if (spanStart != spanEnd) {
                if (spanStart <= start && spanEnd >= end) {
                    s.removeSpan(styleSpan);
                    s.setSpan(new StrikethroughSpan(), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    s.setSpan(new StrikethroughSpan(), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

        }
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        if (editText == null) {
            return;
        }
        boolean Strikethrough_flag = false;
        Editable s = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            StrikethroughSpan[] styleSpans = s.getSpans(selStart - 1, selStart, StrikethroughSpan.class);
            for (StrikethroughSpan styleSpan : styleSpans) {
                if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                    Strikethrough_flag = true;
                    StrikethroughSpan[] styleSpans_next = s.getSpans(selStart, selStart + 1, StrikethroughSpan.class);
                    for (StrikethroughSpan styleSpan_next : styleSpans_next) {

                        if (s.getSpanStart(styleSpan_next) != s.getSpanEnd(styleSpan_next)) {
                            if (styleSpan_next != styleSpan) {
                                setStyle(selStart - 1, selStart + 1);
                            }
                        }

                    }
                }

            }
        } else if (selStart != selEnd) {
            StrikethroughSpan[] styleSpans = s.getSpans(selStart, selEnd, StrikethroughSpan.class);
            for (StrikethroughSpan styleSpan : styleSpans) {

                if (s.getSpanStart(styleSpan) <= selStart
                        && s.getSpanEnd(styleSpan) >= selEnd) {
                    if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                        Strikethrough_flag = true;
                    }
                }

            }
        }
        setFlag(Strikethrough_flag);
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
}
