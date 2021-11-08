package cc.bear3.richeditor.listener;

import android.view.View;

/**
 * 可以点击的span点击事件处理
 */
public interface ISpanClickable {
    boolean isClicked(int x, int y);

    void onViewClicked(View view);
}
