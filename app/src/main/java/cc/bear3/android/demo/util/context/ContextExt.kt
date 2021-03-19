package cc.bear3.android.demo.util.context

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Context扩展函数
 * @author TT
 */
fun Context.startWithAnim(intent: Intent) {
    this.startActivity(intent)

    (this as? Activity)?.let {
//        overridePendingTransition(R.anim.activity_target_in, R.anim.activity_origin_out)
    }
}