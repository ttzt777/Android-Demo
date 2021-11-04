package cc.bear3.richeditor.action;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.StyleSpan;

/**
 * @author TT
 * @since 2021-11-4
 */
public class ActionBold extends ActionItem{
    @Override
    public void applyStyle(int start, int end) {
        if (flag) {
            setStyle(start, end);
        } else {
            removeStyle(start, end);
        }
    }

    @Override
    public void onSelectionChanged(int start, int end) {

    }

    @Override
    protected void onStateChanged() {
        int selStart = editText.getSelectionStart();
        int selEnd = editText.getSelectionEnd();
        if (selStart < selEnd) {
            if (flag) {
                setStyle(selStart, selEnd);
            } else {
                removeStyle(selStart, selEnd);
            }
        }
    }

    private void setStyle(int start, int end) {
        int start_fact = start;
        int end_fact = end;

        Editable s = editText.getEditableText();
        StyleSpan[] styleSpans = s.getSpans(start - 1, end + 1, StyleSpan.class);
        for (StyleSpan styleSpan : styleSpans) {
            if (styleSpan.getStyle() == Typeface.BOLD) {
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
        }
        s.setSpan(new StyleSpan(Typeface.BOLD), start_fact, end_fact, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void removeStyle(int start, int end) {
        Editable s = editText.getEditableText();
        StyleSpan[] styleSpans = s.getSpans(start, end, StyleSpan.class);
        for (StyleSpan styleSpan : styleSpans) {
            if (styleSpan.getStyle() == Typeface.BOLD) {
                int spanStart = s.getSpanStart(styleSpan);
                int spanEnd = s.getSpanEnd(styleSpan);
                if (spanStart != spanEnd) {
                    if (spanStart <= start && spanEnd >= end) {
                        s.removeSpan(styleSpan);
                        s.setSpan(new StyleSpan(Typeface.BOLD), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        s.setSpan(new StyleSpan(Typeface.BOLD), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
    }
}
