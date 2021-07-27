package cc.bear3.android.demo.util.image

import android.widget.ImageView
import cc.bear3.android.demo.R
import cc.bear3.android.demo.util.glide.GlideUtil
import com.bumptech.glide.request.RequestOptions

/**
 *
 * @author TT
 * @since 2021-7-21
 */
fun ImageView.loadImage(
    url: String?, placeHolder: Int = R.color.app_image_place,
    error: Int = R.color.app_image_error,
    requestOptions: RequestOptions = GlideUtil.createRequestOptions(placeHolder, error)
) {
    GlideUtil.loadImage(this, url, this.context, requestOptions)
}