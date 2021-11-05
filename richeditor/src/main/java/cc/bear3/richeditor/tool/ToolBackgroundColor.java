package cc.bear3.richeditor.tool;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;

/**
 * 文字背景高亮色
 */
public class ToolBackgroundColor extends ToolItem {
    private int backgroundColor = Color.TRANSPARENT;

    @Override
    public void applyStyle(int start, int end) {
        if (backgroundColor == Color.TRANSPARENT) {
            removeStyle(start, end);
        } else {
            setStyle(start, end);
        }
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        if (editText == null) {
            return;
        }

        Editable s = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            BackgroundColorSpan[] styleSpans = s.getSpans(selStart - 1, selStart, BackgroundColorSpan.class);
            for (BackgroundColorSpan styleSpan : styleSpans) {
                if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                    backgroundColor = styleSpan.getBackgroundColor();
                }
            }
            if (styleSpans.length == 0) {
                backgroundColor = Color.TRANSPARENT;
            }
        } else if (selStart != selEnd) {
            BackgroundColorSpan[] styleSpans = s.getSpans(selStart, selEnd, BackgroundColorSpan.class);
            for (BackgroundColorSpan styleSpan : styleSpans) {

                if (s.getSpanStart(styleSpan) <= selStart
                        && s.getSpanEnd(styleSpan) >= selEnd) {
                    if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                        backgroundColor = styleSpan.getBackgroundColor();
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

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        notifyStateChanged();
        onStateChanged();
    }

    private void setStyle(int start, int end) {
        int start_fact = start;
        int end_fact = end;
        Editable s = editText.getEditableText();
        BackgroundColorSpan[] styleSpans = s.getSpans(start - 1, end + 1, BackgroundColorSpan.class);
        for (BackgroundColorSpan styleSpan : styleSpans) {
            int spanStart = s.getSpanStart(styleSpan);
            int spanEnd = s.getSpanEnd(styleSpan);
            if (styleSpan.getBackgroundColor() == backgroundColor) {
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
            } else {
                if (spanEnd <= start || spanStart >= end) {

                } else {
                    s.removeSpan(styleSpan);
                    if (spanStart < start) {
                        s.setSpan(new BackgroundColorSpan(styleSpan.getBackgroundColor()), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (spanEnd > end) {
                        s.setSpan(new BackgroundColorSpan(styleSpan.getBackgroundColor()), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
        s.setSpan(new BackgroundColorSpan(backgroundColor), start_fact, end_fact, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void removeStyle(int start, int end) {
        Editable s = editText.getEditableText();
        BackgroundColorSpan[] styleSpans = s.getSpans(start, end, BackgroundColorSpan.class);
        for (BackgroundColorSpan styleSpan : styleSpans) {
            int spanStart = s.getSpanStart(styleSpan);
            int spanEnd = s.getSpanEnd(styleSpan);
            if (spanStart != spanEnd) {
                s.removeSpan(styleSpan);
                if (spanStart < start) {
                    s.setSpan(new BackgroundColorSpan(styleSpan.getBackgroundColor()), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (spanEnd > end) {
                    s.setSpan(new BackgroundColorSpan(styleSpan.getBackgroundColor()), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
    }
}
