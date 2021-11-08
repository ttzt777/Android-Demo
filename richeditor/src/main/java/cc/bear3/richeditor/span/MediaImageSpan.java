package cc.bear3.richeditor.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import cc.bear3.richeditor.listener.ISpanClickable;

/**
 * 图片、视频的span
 */
public class MediaImageSpan extends BlockImageSpan implements ISpanClickable {

    private long id;
    private String uri;

    public MediaImageSpan(@NonNull Drawable drawable, long id, String uri) {
        super(drawable);
        this.id = id;
        this.uri = uri;
    }

    public MediaImageSpan(@NonNull Context context, @NonNull Bitmap bitmap, long id, String uri) {
        super(context, bitmap);
        this.id = id;
        this.uri = uri;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        Log.d("ttzt777", "x - " + x + " | top - " + top + " | y - " + y + " | bottom - " + bottom);
    }

    @Override
    public boolean isClicked(int x, int y) {
        Log.d("ttzt777", "x - " + x + " | y - " + y);
        return false;
    }

    @Override
    public void onViewClicked(View view) {
        Toast.makeText(view.getContext(), "点了删除", Toast.LENGTH_SHORT).show();
    }
}
