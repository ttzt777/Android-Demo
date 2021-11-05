package cc.bear3.richeditor.utils;

import android.text.Layout;
import android.widget.TextView;

/**
 * @author TT
 * @since 2021-11-5
 */
public class RichEditorUtil {
    public static int getTextLead(int offset, TextView textView) {
        int currentLine = getLineForOffset(textView, offset);
        Layout layout = textView.getLayout();
        String text = textView.getText().toString();
        int lead = 0;
        while (currentLine != 0) {
            lead = layout.getLineStart(currentLine);
            char Char = text.charAt(lead - 1);
            if (Char == '\n') {
                break;
            } else {
                lead = 0;
            }
            currentLine--;
        }
        return lead;
    }

    public static int getTextEnd(int offset, TextView textView) {
        int currentLine = getLineForOffset(textView, offset);
        Layout layout = textView.getLayout();
        String text = textView.getText().toString();
        int end = text.length();
        while (currentLine < textView.getLineCount()) {
            end = layout.getLineEnd(currentLine);
            if (end > 0) {
                char Char = text.charAt(end - 1);
                if (Char == '\n') {
                    break;
                }
            }
            currentLine++;
        }
        return end;
    }

    public static int getLineStart(TextView textView, int offset) {
        return textView.getLayout().getLineStart(getLineForOffset(textView, offset));
    }

    public static int getLineEnd(TextView textView, int offset) {
        return textView.getLayout().getLineEnd(getLineForOffset(textView, offset));
    }

    public static int getLineForOffset(TextView textView, int offset) {
        Layout layout = textView.getLayout();
        if (null == layout) {
            return -1;
        }
        return layout.getLineForOffset(offset);
    }
}
