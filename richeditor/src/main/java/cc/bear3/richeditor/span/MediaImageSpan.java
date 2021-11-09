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

    private Drawable originDrawable;
    private Bitmap targetBitmap;

    public MediaImageSpan(@NonNull Context context, @NonNull Bitmap bitmap) {
        super(context, bitmap);
        this.targetBitmap = bitmap;
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

    public void setParams(long id, String uri, @NonNull Drawable drawable) {
        this.id = id;
        this.uri = uri;
        this.originDrawable = drawable;
    }

    public void setParams(MediaImageSpan span) {
        this.id = span.getId();
        this.uri = span.getUri();
        this.originDrawable = span.getOriginDrawable();
    }

    public long getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public Drawable getOriginDrawable() {
        return originDrawable;
    }

    public void recycle() {
        originDrawable = null;

        if (targetBitmap != null) {
            if (!targetBitmap.isRecycled()) {
                targetBitmap.recycle();
            }

            targetBitmap = null;
        }
    }
}
