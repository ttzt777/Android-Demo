package cc.bear3.android.demo.util.date

/**
 *
 * @author TT
 * @since 2021-3-11
 */
class DateUtil {
    companion object {
        fun formatTime(ms: Long) : String {
            val timeSecond = ms / 1000
            val second = timeSecond % 60
            val minute = (timeSecond / 60) % 60
            val hour = second / 3600

            return if (hour > 0) {
                String.format("%d:%02d:%02d", hour, minute, second)
            } else {
                String.format("%02d:%02d", minute, second)
            }
        }
    }
}