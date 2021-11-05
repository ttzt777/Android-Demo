package cc.bear3.richeditor.tool;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.StyleSpan;

/**
 * 加粗
 */
public class ToolBold extends ToolFlagItem {
    @Override
    protected void setStyle(int start, int end) {
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

    @Override
    protected void removeStyle(int start, int end) {
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

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        if (editText == null) {
            return;
        }

        boolean Bold_flag = false;
        Editable s = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            StyleSpan[] styleSpans = s.getSpans(selStart - 1, selStart, StyleSpan.class);
            for (StyleSpan styleSpan : styleSpans) {
                if (styleSpan.getStyle() == Typeface.BOLD) {
                    if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                        Bold_flag = true;
                        StyleSpan[] styleSpans_next = s.getSpans(selStart, selStart + 1, StyleSpan.class);
                        for (StyleSpan styleSpan_next : styleSpans_next) {
                            if (styleSpan_next.getStyle() == Typeface.BOLD) {
                                if (s.getSpanStart(styleSpan_next) != s.getSpanEnd(styleSpan_next)) {
                                    if (styleSpan_next != styleSpan) {
                                        setStyle(selStart - 1, selStart + 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (selStart != selEnd) {
            StyleSpan[] styleSpans = s.getSpans(selStart, selEnd, StyleSpan.class);
            for (StyleSpan styleSpan : styleSpans) {
                if (styleSpan.getStyle() == Typeface.BOLD) {
                    if (s.getSpanStart(styleSpan) <= selStart
                            && s.getSpanEnd(styleSpan) >= selEnd) {
                        if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                            Bold_flag = true;
                        }
                    }
                }
            }
        }
        setFlag(Bold_flag);
    }

    @Override
    protected void onStateChanged() {
        int selStart = editText.getSelectionStart();
        int selEnd = editText.getSelectionEnd();
        if (selStart < selEnd) {
            applyStyle(selStart, selEnd);
        }
    }
}
