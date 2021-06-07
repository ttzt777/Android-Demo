package cc.bear3.android.demo.util

/**
 *
 * @author TT
 * @since 2021-6-7
 */
fun String?.nonNull() : String {
    if (this == null) {
        return ""
    }

    return this
}