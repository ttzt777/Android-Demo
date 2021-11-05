package cc.bear3.richeditor.tool;

import android.widget.EditText;

import java.util.Objects;

/**
 * @author TT
 * @since 2021-11-4
 */
public abstract class ToolItem {
    // 编辑器
    protected EditText editText;

    // 状态改变的回调
    private IToolItemCallback listener;

    public void setToolStateCallback(IToolItemCallback listener) {
        this.listener = listener;
    }

    public void attach(EditText editText) {
        this.editText = editText;
//        if (editText instanceof IToolItemCallback) {
//            setToolStateCallback((IToolItemCallback) editText);
//        }
        notifyStateChanged();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }

        return o.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().getName());
    }

    /**
     * 当文字改变后，为改变的文字添加样式
     */
    public abstract void applyStyle(int start, int end);

    /**
     * 光标改变，需要处理当前的工具flag
     */
    public abstract void onSelectionChanged(int selStart, int selEnd);

    /**
     * 状态改变，从外部选中某个tool状态
     */
    protected abstract void onStateChanged();

    protected void notifyStateChanged() {
        if (listener != null) {
            listener.onToolStateChange(this);
        }
    }
}
