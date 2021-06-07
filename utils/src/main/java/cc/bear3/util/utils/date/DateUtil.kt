package cc.bear3.util.utils.date

import java.text.SimpleDateFormat
import java.util.*

/**
 * desc:
 * time: 2020/7/29
 * @author yinYin
 */
@Suppress("MemberVisibilityCanBePrivate")
class DateUtil {
    companion object {
        fun getTimeFormatString(time: Long, format: String): String {
            val formatter = SimpleDateFormat(format, Locale.CHINA)
            return formatter.format(Date(time))
        }

        fun formatTime(
                time: String?,
                toFormat: String = "yyyy-MM-dd",
                format: String = "yyyy-MM-dd HH:mm:ss"
        ): String {
            if (time == null) {
                return ""
            }
            return try {
                val formatter = SimpleDateFormat(format, Locale.CHINA)
                val timeMs = formatter.parse(time)?.time ?: 0

                getTimeFormatString(timeMs, toFormat)
            } catch (e: java.lang.Exception) {
                time
            }

        }

        fun formatTimeLong(time: String?, format: String = "yyyy-MM-dd HH:mm:ss"): Long {
            if (time == null) {
                return System.currentTimeMillis()
            }
            return try {
                SimpleDateFormat(format, Locale.getDefault()).parse(time)?.time
                        ?: System.currentTimeMillis()
            } catch (e: Exception) {
                System.currentTimeMillis()
            }

        }
    }
}