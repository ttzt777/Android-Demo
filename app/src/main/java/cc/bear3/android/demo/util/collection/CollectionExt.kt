package cc.bear3.android.demo.util.collection

/**
 *
 * @author TT
 * @since 2021-7-15
 */
fun Collection<*>?.size() : Int {
    if (this == null) {
        return 0
    }

    return this.size
}