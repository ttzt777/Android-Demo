package cc.bear3.android.demo.util.glide

import android.content.Context
import android.widget.ImageView
import cc.bear3.android.demo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 *
 * @author TT
 * @since 2021-7-21
 */
class GlideUtil {
    companion object {
        fun loadImage(
            imageView: ImageView,
            url: String?,
            context: Context = imageView.context,
            requestOptions: RequestOptions = createRequestOptions()
        ) {
            Glide.with(context).load(url).apply(requestOptions).into(imageView)
        }

        fun createRequestOptions(
            placeHolder: Int = R.color.app_image_place,
            error: Int = R.color.app_image_error
        ): RequestOptions {
            return RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .skipMemoryCache(false)
                .placeholder(placeHolder)
                .error(error)
        }
    }
}