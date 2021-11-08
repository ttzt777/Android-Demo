package cc.bear3.richeditor.helper;

import android.text.Editable;
import android.util.TypedValue;
import android.widget.EditText;

import java.util.Objects;

/**
 * Helper基类，主要用于处理添加某个span到EditText中，不涉及到后续的文本样式
 */
public abstract class HelperItem {
    // 编辑器
    protected EditText editText;

    public void attach(EditText editText) {
        this.editText = editText;
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

    protected void insertCharSequence(CharSequence content, int pos) {
        if (editText == null) {
            return;
        }

        Editable editable = editText.getEditableText();//获得文本内容
        if (pos < 0 || pos >= editable.length()) {
            editable.append(content);
            editText.setSelection(editable.length());
        } else {
            editable.insert(pos, content);
            editText.setSelection(pos + content.length());
        }
    }

    protected int dp2px(int dp) {
        if (editText == null || editText.getContext() == null) {
            return 0;
        }

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, editText.getContext().getResources().getDisplayMetrics());
    }
}
