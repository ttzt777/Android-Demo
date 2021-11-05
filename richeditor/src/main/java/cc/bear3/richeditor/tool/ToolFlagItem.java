package cc.bear3.richeditor.tool;

/**
 * @author TT
 * @since 2021-11-5
 */
public abstract class ToolFlagItem extends ToolItem {
    // 状态flag，是否加粗、斜体等等，主要看子类是用来干嘛的
    protected boolean flag = false;

    public boolean getFlag() {
        return flag;
    }

    /**
     * 外部调用，取反当前状态
     */
    public void toggleFlag() {
        this.flag = !flag;

        notifyStateChanged();

        if (editText != null) {
            onStateChanged();
        }
    }

    protected void setFlag(boolean target) {
        this.flag = target;

        notifyStateChanged();
    }

    @Override
    public void applyStyle(int start, int end) {
        if (flag) {
            setStyle(start, end);
        } else {
            removeStyle(start, end);
        }
    }

    protected abstract void setStyle(int start, int end);

    protected abstract void removeStyle(int start, int end);
}
