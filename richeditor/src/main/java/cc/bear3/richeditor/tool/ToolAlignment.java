package cc.bear3.richeditor.tool;

import android.text.Editable;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.LeadingMarginSpan;

import cc.bear3.richeditor.utils.RichEditorUtil;

/**
 * 文字对齐方式
 */
public class ToolAlignment extends ToolItem {
    private Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;

    @Override
    public void applyStyle(int start, int end) {

    }

    @Override
    public void onSelectionChanged(int selStart, int selEnd) {
        Editable s = editText.getEditableText();
        int lineStart = RichEditorUtil.getLineStart(editText, selStart);
        AlignmentSpan.Standard[] currentSpan = s.getSpans(lineStart, lineStart + 1, AlignmentSpan.Standard.class);
        for (AlignmentSpan.Standard span : currentSpan) {
            alignment = span.getAlignment();
        }

        if (selStart == selEnd) {
            if (selStart > 0 && selStart < s.length() && s.charAt(selStart - 1) != '\n') {
                int textLead = RichEditorUtil.getTextLead(selStart - 1, editText);
                int textEnd = RichEditorUtil.getTextEnd(selStart - 1, editText);

                AlignmentSpan.Standard[] spansLast = s.getSpans(selStart - 1, selStart, AlignmentSpan.Standard.class);
                if (spansLast != null && spansLast.length > 0) {
                    AlignmentSpan.Standard spanLast = spansLast[spansLast.length - 1];
                    int spanStart = s.getSpanStart(spanLast);
                    int spanEnd = s.getSpanEnd(spanLast);
                    if (spanStart > textLead || spanEnd < textEnd) {
                        removeSpan(s, textLead, textEnd);
                        s.setSpan(new AlignmentSpan.Standard(spanLast.getAlignment()), textLead, textEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    }
                } else {
                    removeSpan(s, textLead, textEnd);
                }
            }
            int lineEnd = RichEditorUtil.getLineEnd(editText, selStart);
            if (lineEnd > 0 && lineEnd <= s.length() && s.charAt(lineEnd - 1) == '\u200B') {
                if (lineEnd - 1 != lineStart) {
                    LeadingMarginSpan[] listSpans = s.getSpans(lineEnd - 1, lineEnd, LeadingMarginSpan.class);
                    if (listSpans != null && listSpans.length > 0) {
                        s.delete(lineEnd - 1, lineEnd);
                        return;
                    }
                }
            }
            if (selStart > 0) {
                char Char = s.charAt(selStart - 1);
                if (Char == '\u200B') {
                    LeadingMarginSpan[] leadingMarginSpans = s.getSpans(selStart - 1, selStart, LeadingMarginSpan.class);
                    if (leadingMarginSpans == null || leadingMarginSpans.length == 0) {
                        editText.setSelection(selStart - 1);
                        return;
                    }
                }
            }

            if ((selStart == 0 || s.charAt(selStart - 1) == '\n') && selStart == s.length() && s.length() != 0) {
                s.replace(selStart, selStart, "\u200B");
            }
        }

        notifyStateChanged();
    }

    @Override
    protected void onStateChanged() {
        if (editText == null) {
            return;
        }

        Editable s = editText.getEditableText();
        int selStart = editText.getSelectionStart();
        int selEnd = editText.getSelectionEnd();
        int textLead = RichEditorUtil.getTextLead(selStart, editText);
        int textEnd = RichEditorUtil.getTextEnd(selEnd, editText);


        if (textLead == textEnd) {
            s.insert(textLead, "\u200B");
            s.setSpan(new AlignmentSpan.Standard(alignment), textLead, textLead + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }


        for (int i = textLead; i < textEnd; ) {
            int end = RichEditorUtil.getTextEnd(i, editText);
            AlignmentSpan.Standard[] alignmentSpans = s.getSpans(i, end, AlignmentSpan.Standard.class);
            for (AlignmentSpan.Standard alignmentSpan : alignmentSpans) {
                int spanStart = s.getSpanStart(alignmentSpan);
                int spanEnd = s.getSpanEnd(alignmentSpan);
                s.removeSpan(alignmentSpan);
                if (spanStart < i) {
                    s.setSpan(new AlignmentSpan.Standard(alignmentSpan.getAlignment()), spanStart, i, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
                if (spanEnd > end) {
                    s.setSpan(new AlignmentSpan.Standard(alignmentSpan.getAlignment()), end, spanEnd, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }
            }
            LeadingMarginSpan[] leadingMarginSpans = s.getSpans(i, end, LeadingMarginSpan.class);
            if (leadingMarginSpans == null || leadingMarginSpans.length == 0) {
                s.setSpan(new AlignmentSpan.Standard(alignment), i, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            i = end;
        }
    }

    public Layout.Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Layout.Alignment alignment) {
        this.alignment = alignment;
        notifyStateChanged();
        onStateChanged();
    }

    protected void removeSpan(Editable s, int textLead, int textEnd) {
        AlignmentSpan.Standard[] spans = s.getSpans(textLead, textEnd, AlignmentSpan.Standard.class);
        for (AlignmentSpan.Standard span : spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            if (start < textLead) {
                s.setSpan(new AlignmentSpan.Standard(span.getAlignment()), start, textLead, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            if (end > textEnd) {
                s.setSpan(new AlignmentSpan.Standard(span.getAlignment()), textEnd, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
    }
}
