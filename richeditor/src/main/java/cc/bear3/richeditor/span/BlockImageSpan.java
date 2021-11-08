package cc.bear3.richeditor.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/**
 * 占满全屏的图片，这一层用来做判定区分
 */
public class BlockImageSpan extends ImageSpan {
    public BlockImageSpan(@NonNull Context context, @NonNull Bitmap bitmap) {
        super(context, bitmap);
    }

    public BlockImageSpan(@NonNull Context context, @NonNull Bitmap bitmap, int verticalAlignment) {
        super(context, bitmap, verticalAlignment);
    }

    public BlockImageSpan(@NonNull Drawable drawable) {
        super(drawable);
    }

    public BlockImageSpan(@NonNull Drawable drawable, int verticalAlignment) {
        super(drawable, verticalAlignment);
    }

    public BlockImageSpan(@NonNull Drawable drawable, @NonNull String source) {
        super(drawable, source);
    }

    public BlockImageSpan(@NonNull Drawable drawable, @NonNull String source, int verticalAlignment) {
        super(drawable, source, verticalAlignment);
    }

    public BlockImageSpan(@NonNull Context context, @NonNull Uri uri) {
        super(context, uri);
    }

    public BlockImageSpan(@NonNull Context context, @NonNull Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public BlockImageSpan(@NonNull Context context, int resourceId) {
        super(context, resourceId);
    }

    public BlockImageSpan(@NonNull Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }
}
