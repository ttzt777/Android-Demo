package cc.bear3.richeditor.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.HashSet;
import java.util.Set;

import cc.bear3.richeditor.helper.HelperItem;
import cc.bear3.richeditor.movement.SpanClickableLinkMovementMethod;
import cc.bear3.richeditor.tool.IToolItemCallback;
import cc.bear3.richeditor.tool.ToolFlagItem;
import cc.bear3.richeditor.tool.ToolItem;

/**
 * @author TT
 * @since 2021-11-4
 */
public class RichEditor extends AppCompatEditText implements IToolItemCallback {
    private final Set<ToolItem> tools = new HashSet<>();
    private final Set<HelperItem> helpers = new HashSet<>();

    // 状态改变的回调
    private IToolItemCallback listener;

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
        this.setMovementMethod(new SpanClickableLinkMovementMethod());
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if (getEditableText() == null || tools == null || tools.size() == 0) {
            return;
        }

        for (ToolItem action : tools) {
            action.onSelectionChanged(selStart, selEnd);
        }
    }

    @Override
    public void onToolStateChange(ToolItem tool) {
        if (tools == null || tools.size() == 0 || listener == null) {
            return;
        }

        listener.onToolStateChange(tool);
    }

    public void addToolItems(ToolItem... toolItems) {
        for (ToolItem tool : toolItems) {
            if (tools.add(tool)) {
                tool.attach(this);
            }
        }
    }

    public ToolItem getToolItem(Class<? extends ToolItem> clazz) {
        if (tools == null || tools.size() == 0) {
            return null;
        }

        for (ToolItem temp : tools) {
            if (temp.getClass() == clazz) {
                return temp;
            }
        }

        return null;
    }

    public void addHelperItems(HelperItem... helperItems) {
        for (HelperItem helper : helperItems) {
            if (helpers.add(helper)) {
                helper.attach(this);
            }
        }
    }

    public HelperItem getHelperItem(Class<? extends HelperItem> clazz) {
        if (helpers == null || helpers.size() == 0) {
            return null;
        }

        for (HelperItem temp : helpers) {
            if (temp.getClass() == clazz) {
                return temp;
            }
        }

        return null;
    }

    public void setToolStateCallback(IToolItemCallback listener) {
        this.listener = listener;
    }

    public void toggleToolFlag(Class<? extends ToolFlagItem> clazz) {
        if (tools == null || tools.size() == 0) {
            return;
        }

        for (ToolItem tool : tools) {
            if (tool instanceof ToolFlagItem && tool.getClass() == clazz) {
                ((ToolFlagItem) tool).toggleFlag();
            }
        }
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
                    for (ToolItem action : tools) {
                        action.applyStyle(input_start, input_end);
                    }
                }
            }
        };
        addTextChangedListener(textWatcher);
    }
}
