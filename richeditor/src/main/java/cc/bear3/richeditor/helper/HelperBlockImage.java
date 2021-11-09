package cc.bear3.richeditor.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import cc.bear3.richeditor.R;
import cc.bear3.richeditor.span.BlockImageSpan;
import cc.bear3.richeditor.span.MediaImageSpan;
import cc.bear3.richeditor.utils.ViewUtil;

/**
 * 图片（视频）插入工具
 */
public class HelperBlockImage extends HelperItem {
    private static final int IMAGE_PADDING_NORMAL = 10;
    private static final int IMAGE_PADDING_BOTTOM = 6;
    private static final int IMAGE_DELETE_SIZE = 18;
    private static final int STATUS_HEIGHT = 30;
    private static final float PROGRESS_HEIGHT = 2.5f;
    private static final int IMAGE_RADIUS = 5;

    private final IdGenerator idGenerator;

    private final Map<String, Set<Long>> uriIdMap;

    private final Paint bitmapPaint;
    private final Paint paint;

    public HelperBlockImage() {
        idGenerator = new IdGenerator();
        uriIdMap = new HashMap<>();

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setDither(true);
        bitmapPaint.setFilterBitmap(true);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(sp2px(12));
    }

    /**
     * 初次插入图片
     */
    public void insertImage(@NonNull String uri, @NonNull Drawable drawable) {
        Bitmap targetBitmap = getImageBitmap(drawable, 0);

        if (editText == null || targetBitmap == null) {
            return;
        }

        long id = idGenerator.nextId();

        Set<Long> idSet = uriIdMap.get(uri);
        if (idSet == null) {
            idSet = new HashSet<>();
        }
        idSet.add(id);
        uriIdMap.put(uri, idSet);

        MediaImageSpan span = new MediaImageSpan(editText.getContext(), targetBitmap);
        span.setParams(id, uri, drawable);
        insertImageSpan(span);
    }

    /**
     * 改变进度
     */
    public void changeProgress(@NonNull String uri, int progress) {
        if (editText == null || editText.getText() == null) {
            return;
        }

        // 找到uri对应的span id集合
        Set<Long> idSet = uriIdMap.get(uri);

        if (idSet == null || idSet.size() == 0) {
            return;
        }

        // 从整个文本找到对应的span
        MediaImageSpan[] spans = editText.getText().getSpans(0, editText.getText().length(), MediaImageSpan.class);

        if (spans == null || spans.length == 0) {
            return;
        }

        for (MediaImageSpan span : spans) {
            if (!uri.equals(span.getUri())) {
                continue;
            }

            MediaImageSpan targetSpan = new MediaImageSpan(editText.getContext(), getImageBitmap(span.getOriginDrawable(), progress));
            targetSpan.setParams(span);
            // 改变原来span的进度
            replaceImageSpan(span, targetSpan);
        }
    }

    /**
     * 将图片对应的drawable转换成特定样式的bitmap
     */
    @Nullable
    private Bitmap getImageBitmap(@NonNull Drawable drawable, int progress) {
        if (editText == null || editText.getContext() == null) {
            return null;
        }

        LayoutInflater inflater = LayoutInflater.from(editText.getContext());
        View view = inflater.inflate(R.layout.image_bitmap_layout, null);
        CardView contentView = view.findViewById(R.id.content);
        ImageView coverView = view.findViewById(R.id.cover);
        View hintBgView = view.findViewById(R.id.hint_background);
        TextView hintTextView = view.findViewById(R.id.hint_text);
        View progressView = view.findViewById(R.id.progress);

        coverView.setImageDrawable(drawable);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) contentView.getLayoutParams();

        int resultWidth = editText.getWidth() - editText.getPaddingStart() - editText.getPaddingEnd();
        int contentWidth = resultWidth - layoutParams.getMarginStart() - layoutParams.getMarginEnd();
        int contentHeight = (int) ((float) drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth() * contentWidth);
        int resultHeight = contentHeight + layoutParams.topMargin + layoutParams.bottomMargin;

        layoutParams.width = contentWidth;
        layoutParams.height = contentHeight;

        if (progress < 100) {
            hintBgView.setVisibility(View.VISIBLE);
            hintTextView.setVisibility(View.VISIBLE);


            if (progress >= 0) {
                // 正在上传
                hintTextView.setText(String.format(Locale.CHINA, "正在上传...%d%%", progress));

                progressView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams progressLayoutParams = progressView.getLayoutParams();
                progressLayoutParams.width = (int) (progress / 100f * contentWidth);
            } else {
                // 出错
                hintTextView.setText("上传出错");

                progressView.setVisibility(View.GONE);
            }

        } else {
            hintBgView.setVisibility(View.GONE);
            hintTextView.setVisibility(View.GONE);
            progressView.setVisibility(View.GONE);
        }


        ViewUtil.layoutView(view, resultWidth, resultHeight);

        Bitmap result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(result);
        view.draw(canvas);

//        int paddingNormal = dp2px(IMAGE_PADDING_NORMAL);
//        int paddingBottom = dp2px(IMAGE_PADDING_BOTTOM);
//        int deleteSize = dp2px(IMAGE_DELETE_SIZE);
//
//        int resultWidth = editText.getWidth() - editText.getPaddingStart() - editText.getPaddingEnd();
//
//        // 内容bitmap，包含背景色、loading、进度条
//        Bitmap contentBitmap = getImageContentBitmap(resultWidth, bitmap, progress);
//
//        int resultHeight = contentBitmap.getHeight() + paddingNormal + paddingBottom;
//
//        // 开始处理图片
//        Bitmap result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(result);
//
////        bitmapPaint.setXfermode(null);
//
//        // 画出内容bitmap
//        int radius = dp2px(IMAGE_RADIUS);
//        RectF contentRectF = new RectF(paddingNormal, paddingNormal, resultWidth - paddingNormal, resultHeight - paddingBottom);
////        canvas.drawRoundRect(contentRectF, radius, radius, bitmapPaint);
////        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(
//                bitmap,
//                null,
//                contentRectF,
//                bitmapPaint);
//
//        // 删除按钮
////        bitmapPaint.setXfermode(null);
//        canvas.drawBitmap(
//                BitmapFactory.decodeResource(editText.getContext().getResources(), R.mipmap.ic_close),
//                null,
//                new Rect(resultWidth - deleteSize, 0, resultWidth, deleteSize),
//                bitmapPaint);

        return result;
    }

    /**
     * 将图片对应的drawable转换bitmap
     * 包含图片背景、loading状态、进度条
     */
    private Bitmap getImageContentBitmap(int width, @NonNull Bitmap bitmap, int progress) {
        int paddingHor = dp2px(IMAGE_PADDING_NORMAL);

        int resultWidth = width - paddingHor * 2;
        int resultHeight = (int) ((float) bitmap.getHeight() / bitmap.getWidth() * resultWidth);

        // 当前需要绘制蒙层的时候保证一下最小高度
        if (progress < 100) {
            resultHeight = Math.max(resultHeight, dp2px(STATUS_HEIGHT));
        }

        Bitmap result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        // 图片背景
//        bitmapPaint.setXfermode(null);
        canvas.drawBitmap(
                bitmap,
                null,
                new Rect(0, 0, resultWidth, resultHeight),
                bitmapPaint);

        progress = 10;

        // 上传\出错状态，需要绘制（完成状态不绘制）
        if (progress < 100) {
            // 画蒙层
            int statusHeight = dp2px(STATUS_HEIGHT);
            paint.setColor(0x7F03132C);
            RectF rectf = new RectF(0, resultHeight - statusHeight, resultWidth, resultHeight);
            canvas.drawRect(rectf, paint);

            // 画文字
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float baseline = rectf.centerY() + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            float x = dp2px(12);

            if (progress < 0) {
                // 出错状态
                paint.setColor(0xFFF6F8FD);
                canvas.drawText("上传出错", x, baseline, paint);
            } else {
                // 上传状态
                paint.setColor(0xFFF6F8FD);
                canvas.drawText(String.format(Locale.CHINA, "正在上传...%d%%", progress), x, baseline, paint);
            }
        }

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
     * 插入图片span
     */
    private void replaceImageSpan(@NonNull MediaImageSpan origin, @NonNull MediaImageSpan target) {
        SpannableString spannableString = new SpannableString("[image]");
        spannableString.setSpan(target, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int start = editText.getText().getSpanStart(origin);
        int end = editText.getText().getSpanEnd(origin);

        editText.getText().removeSpan(origin);

        editText.getText().replace(start, end, spannableString);
        origin.recycle();
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

    private static class IdGenerator {
        private long id = 0;

        synchronized long nextId() {
            return id++;
        }
    }
}
