package cc.bear3.richeditor.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cc.bear3.richeditor.R;
import cc.bear3.richeditor.span.BlockImageSpan;
import cc.bear3.richeditor.span.MediaImageSpan;

/**
 * 图片（视频）插入工具
 */
public class HelperBlockImage extends HelperItem {
    private static final int IMAGE_PADDING = 8;
    private static final int IMAGE_DELETE_SIZE = 24;

    private final IdGenerator idGenerator;

    private final Map<String, Set<Long>> uriIdMap;

    private final Paint bitmapPaint;
    private Bitmap deleteBitmap;

    public HelperBlockImage() {
        idGenerator = new IdGenerator();
        uriIdMap = new HashMap<>();

        bitmapPaint = new Paint();
        bitmapPaint.setDither(true);
        bitmapPaint.setFilterBitmap(true);
    }

    /**
     * 初次插入图片
     */
    public void insertImage(@NonNull String uri, @NonNull Bitmap bitmap) {
        insertBitmap(uri, getImageBitmap(bitmap, false, 0));
    }

    /**
     * 初次插入视频
     */
    public void insertVideo(@NonNull String uri, @NonNull Bitmap bitmap) {
        insertBitmap(uri, getImageBitmap(bitmap, true, 0));
    }

    /**
     * 改变视频封面（适用于一开始是占位，异步获取封面图完成后改变）
     */
    public void changeVideoCover(@NonNull String uri, @NonNull Bitmap bitmap, int progress) {

    }

    /**
     * 改变进度
     */
    public void changeProgress(@NonNull String uri, int progress) {

    }

    /**
     * 初次插入图片、视频
     */
    private void insertBitmap(@NonNull String uri, @Nullable Bitmap bitmap) {
        if (editText == null || bitmap == null) {
            return;
        }

        long id = idGenerator.nextId();

        Set<Long> idSet = uriIdMap.get(uri);
        if (idSet == null) {
            idSet = new HashSet<>();
        }
        idSet.add(id);
        uriIdMap.put(uri, idSet);

        MediaImageSpan span = new MediaImageSpan(editText.getContext(), bitmap, id, uri);
        insertImageSpan(span);
    }

    /**
     * 将图片、视频对应的drawable转换成特定样式的bitmap
     */
    @Nullable
    private Bitmap getImageBitmap(@NonNull Bitmap bitmap, boolean isVideo, int progress) {
        if (editText == null || editText.getContext() == null) {
            return null;
        }

        int padding = dp2px(IMAGE_PADDING);
        int deleteSize = dp2px(IMAGE_DELETE_SIZE);

        int resultWidth = editText.getWidth() - editText.getPaddingStart() - editText.getPaddingEnd();

        int targetBitmapWidth = resultWidth - padding * 2;
        int targetBitmapHeight = (int) ((float) bitmap.getHeight() / bitmap.getWidth() * targetBitmapWidth);

        int resultHeight = targetBitmapHeight + padding * 2;

        // 开始处理图片
        Bitmap result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        // 画出图片
        canvas.drawBitmap(
                bitmap,
                null,
                new Rect(padding, padding, padding + targetBitmapWidth, padding + targetBitmapHeight),
                bitmapPaint);

        // 删除按钮
        canvas.drawBitmap(
                BitmapFactory.decodeResource(editText.getContext().getResources(), R.mipmap.ic_close),
                null,
                new Rect(resultWidth - deleteSize, 0, resultWidth, deleteSize),
                bitmapPaint);

        return result;
    }

    /**
     * 插入图片span
     */
    private void insertImageSpan(@NonNull BlockImageSpan span) {
        if (editText == null) {
            return;
        }

        if (!isCursorInFirstIndexOfLine()) {
            insertCharSequence("\n", editText.getSelectionStart());
        }

        SpannableString spannableString = new SpannableString("[image]");
        spannableString.setSpan(span, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        insertCharSequence(spannableString, editText.getSelectionStart());

        insertCharSequence("\n", editText.getSelectionStart());
    }

    /**
     * 判断光标是否处于行首
     */
    private boolean isCursorInFirstIndexOfLine() {
        int cursor = editText.getSelectionStart();
        if (cursor < 0 || editText.length() <= 0) {
            return true;
        }

        Editable editable = editText.getEditableText();
        return cursor == 0 || editable.charAt(cursor - 1) == '\n';
    }

    private Bitmap getDeleteBitmap() {
        if (deleteBitmap == null) {
            deleteBitmap = BitmapFactory.decodeResource(editText.getContext().getResources(), R.mipmap.ic_close);
        }

        return deleteBitmap;
    }

    private static class IdGenerator {
        private long id = 0;

        synchronized long nextId() {
            return id++;
        }
    }
}
