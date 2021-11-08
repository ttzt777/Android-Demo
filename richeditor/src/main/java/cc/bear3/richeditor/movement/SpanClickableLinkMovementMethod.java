package cc.bear3.richeditor.movement;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

import cc.bear3.richeditor.listener.ISpanClickable;

/**
 * @author TT
 * @since 2021-11-8
 */
public class SpanClickableLinkMovementMethod extends LinkMovementMethod {
    private ISpanClickable spanClickable;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                spanClickable = getPressedSpan(widget, buffer, event);
                break;

            case MotionEvent.ACTION_MOVE:
                ISpanClickable touchedSpan = getPressedSpan(widget, buffer, event);
                if (spanClickable != null && touchedSpan != spanClickable) {
                    spanClickable = null;
                    safeRemoveSpan(buffer);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                if (spanClickable != null) {
                    spanClickable.onViewClicked(widget);
                    spanClickable = null;
                }
                break;

            default:
                spanClickable = null;
                break;
        }

        return true;
    }

    private ISpanClickable getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX() - textView.getTotalPaddingLeft() + textView.getScrollX();
        int y = (int) event.getY() - textView.getTotalPaddingTop() + textView.getScrollY();

        Layout layout = textView.getLayout();
        int position = layout.getOffsetForHorizontal(layout.getLineForVertical(y), x);

        ISpanClickable[] blockImageSpans = spannable.getSpans(position, position, ISpanClickable.class);
        ISpanClickable touchedSpan = null;
        if (blockImageSpans.length > 0 && positionWithinTag(position, spannable, blockImageSpans[0])
                && blockImageSpans[0].isClicked(x, y)) {
            touchedSpan = blockImageSpans[0];
        }

        return touchedSpan;
    }

    private boolean positionWithinTag(int position, Spannable spannable, Object tag) {
        return position >= spannable.getSpanStart(tag) && position <= spannable.getSpanEnd(tag);
    }

    private void safeRemoveSpan(Spannable spannable) {
        if (!spannable.toString().isEmpty()) {
            Selection.removeSelection(spannable);
        }
    }
}
