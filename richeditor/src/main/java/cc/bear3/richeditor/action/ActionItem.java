package cc.bear3.richeditor.action;

import android.text.Editable;
import android.widget.EditText;

/**
 * @author TT
 * @since 2021-11-4
 */
public abstract class ActionItem {
    protected EditText editText;

    protected boolean flag = false;

    private IActionItemCallback listener;

    public void setActionCallback(IActionItemCallback listener) {
        this.listener = listener;
    }

    public void attach(EditText editText) {
        this.editText = editText;
    }

    public boolean getFlag() {
        return flag;
    }

    public void toggleFlag() {
        this.flag = !flag;

        notifyStateChanged();

        if (editText != null) {
            onStateChanged();
        }
    }

    public abstract void applyStyle(int start, int end);

    public abstract void onSelectionChanged(int start, int end);

    protected abstract void onStateChanged();

    protected void notifyStateChanged() {
        if (listener != null) {
            listener.onActionStateChange(this);
        }
    }
}
