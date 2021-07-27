package cc.bear3.android.demo.util.file

import android.content.Context
import android.os.Environment
import java.io.File

/**
 *
 * @author TT
 * @since 2021-7-27
 */
class FileUtil {
    companion object {
        fun getPrivateImageCacheDir(context: Context): File? {
            return getPrivateCacheDir(context, "/image")
        }

        fun getPrivateCacheDir(context: Context, type: String): File? {
            val dir =
                if (isExternalStorageWritable() && context.getExternalFilesDir(type) != null) {
                    context.getExternalFilesDir(type)
                } else {
                    File(context.filesDir, type)
                }
            if (dir != null && !dir.exists()) {
                dir.mkdirs()
            }
            return dir
        }

        fun isExternalStorageWritable(): Boolean {
            val state = Environment.getExternalStorageState()
            return "mounted" == state
        }
    }
}