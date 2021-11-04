package cc.bear3.richeditor.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.List;

import cc.bear3.richeditor.action.ActionItem;

/**
 * @author TT
 * @since 2021-11-4
 */
public class RichEditor extends AppCompatEditText {
    private final List<ActionItem> actionItems = new ArrayList<>();

    public RichEditor(Context context) {
        this(context, null);
    }

    public RichEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.requestFocus();
        this.setBackgroundColor(0);
        this.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
                | EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        registerTextWatcher();
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if (getEditableText() == null || actionItems == null || actionItems.size() == 0) {
            return;
        }

        for (ActionItem action : actionItems) {
            action.onSelectionChanged(selStart, selEnd);
        }
    }

    public void addActionItem(ActionItem action) {
        action.attach(this);
        actionItems.add(action);
    }

    private void registerTextWatcher() {
        TextWatcher textWatcher = new TextWatcher() {
            int input_start;
            int input_end;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input_start = start;
                input_end = start + count;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getEditableText() == null) {
                    return;
                }

                if (input_end > input_start) {
                    for (ActionItem action : actionItems) {
                        action.applyStyle(input_start, input_end);
                    }
                }
            }
        };
        addTextChangedListener(textWatcher);
    }
}
