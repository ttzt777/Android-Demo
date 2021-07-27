package cc.bear3.android.demo.util.glide

import android.content.Context
import cc.bear3.android.demo.util.file.FileUtil
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class CustomAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val file = FileUtil.getPrivateImageCacheDir(context)

        file?.let {
            val diskCacheSize = 524288000L
            builder.setDiskCache(DiskLruCacheFactory(it.absolutePath, diskCacheSize))
            val memoryCacheSize = 62914560
            builder.setMemoryCache(LruResourceCache(memoryCacheSize.toLong()))
            builder.setBitmapPool(LruBitmapPool(memoryCacheSize.toLong()))
            builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
        }
    }

//    fun getGlide4_SafeKey(url: String?): String? {
//        try {
//            val messageDigest = MessageDigest.getInstance("SHA-256")
//            val signature = EmptySignature.obtain()
//            signature.updateDiskCacheKey(messageDigest)
//            GlideUrl(url).updateDiskCacheKey(messageDigest)
//            val safeKey = Util.sha256BytesToHex(messageDigest.digest())
//            return "$safeKey.0"
//        } catch (e: Exception) {
//        }
//        return null
//    }
}