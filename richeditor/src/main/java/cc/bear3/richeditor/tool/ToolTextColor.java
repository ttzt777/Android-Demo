package cc.bear3.richeditor.tool;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

/**
 * 文字颜色
 */
public class ToolTextColor extends ToolItem {
    private int textColor = Color.BLACK;

    @Override
    public void attach(EditText editText) {
        textColor = editText.getCurrentTextColor();

        super.attach(editText);
    }

    @Override
    public void applyStyle(int start, int end) {
        int start_fact = start;
        int end_fact = end;
        Editable s = editText.getEditableText();
        ForegroundColorSpan[] styleSpans = s.getSpans(start - 1, end + 1, ForegroundColorSpan.class);
        for (ForegroundColorSpan styleSpan : styleSpans) {
            int spanStart = s.getSpanStart(styleSpan);
            int spanEnd = s.getSpanEnd(styleSpan);
            if (styleSpan.getForegroundColor() == textColor) {
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
                        s.setSpan(new ForegroundColorSpan(styleSpan.getForegroundColor()), spanStart, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (spanEnd > end) {
                        s.setSpan(new ForegroundColorSpan(styleSpan.getForegroundColor()), end, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }

        }
        s.setSpan(new ForegroundColorSpan(textColor), start_fact, end_fact, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        if (editText == null) {
            return;
        }

        Editable s = editText.getEditableText();
        if (selStart > 0 && selStart == selEnd) {
            ForegroundColorSpan[] styleSpans = s.getSpans(selStart - 1, selStart, ForegroundColorSpan.class);
            for (ForegroundColorSpan styleSpan : styleSpans) {
                if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                    textColor = styleSpan.getForegroundColor();
                }
            }
        } else if (selStart != selEnd) {
            ForegroundColorSpan[] styleSpans = s.getSpans(selStart, selEnd, ForegroundColorSpan.class);
            for (ForegroundColorSpan styleSpan : styleSpans) {

                if (s.getSpanStart(styleSpan) <= selStart
                        && s.getSpanEnd(styleSpan) >= selEnd) {
                    if (s.getSpanStart(styleSpan) != s.getSpanEnd(styleSpan)) {
                        textColor = styleSpan.getForegroundColor();
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

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        notifyStateChanged();
        onStateChanged();
    }
}
