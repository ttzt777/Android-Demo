package cc.bear3.richeditor.tool;

import android.text.Editable;
import android.text.Spanned;
import android.text.style.UnderlineSpan;

/**
 * 下划线
 */
public class ToolUnderline extends ToolFlagItem{
    @Override
    protected void setStyle(int start, int end) {
        int start_fact = start;
        int end_fact = end;
        Editable s = editText.getEditableText();
        UnderlineSpan[] styleSpans = s.getSpans(start - 1, end + 1, UnderlineSpan.class);
        for (UnderlineSpan styleSpan : styleSpans) {

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
        s.setSpan(new UnderlineSpan(), start_fact, end_fact, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    protected void removeStyle(int start, int end) {
        Editable s = editText.getEditableText();
        UnderlineSpan[] styleSpans = s.getSpans(start, end, UnderlineSpan.class);
        for (UnderlineSpan styleSpan : styleSpans) {

            int spanStart = s.getSpanStart(styleSpan);
            int spanEnd = s.getSpanEnd(styleSpan);
            if (spanStart != spanEnd) {
                if (spanStart <= start && spanEnd >= end) {
                    s.removeSpan(styleSpan);
                    s.setSpan(new UnderlineSpan(), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    s.setSpan(new UnderlineSpan(), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        if (editText == null) {
            return;
        }
        boolean Underline_flag = false;
        Editable s = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            UnderlineSpan[] styleSpans = s.getSpans(selStart - 1, selStart, UnderlineSpan.class);
            for (UnderlineSpan styleSpan : styleSpans) {
                if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                    Underline_flag = true;
                    UnderlineSpan[] styleSpans_next = s.getSpans(selStart, selStart + 1, UnderlineSpan.class);
                    for (UnderlineSpan styleSpan_next : styleSpans_next) {
                        if (s.getSpanStart(styleSpan_next) != s.getSpanEnd(styleSpan_next)) {
                            if (styleSpan_next != styleSpan) {
                                setStyle(selStart - 1, selStart + 1);
                            }
                        }
                    }
                }

            }
        } else if (selStart != selEnd) {
            UnderlineSpan[] styleSpans = s.getSpans(selStart, selEnd, UnderlineSpan.class);
            for (UnderlineSpan styleSpan : styleSpans) {

                if (s.getSpanStart(styleSpan) <= selStart
                        && s.getSpanEnd(styleSpan) >= selEnd) {
                    if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                        Underline_flag = true;
                    }
                }

            }
        }
        setFlag(Underline_flag);
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
