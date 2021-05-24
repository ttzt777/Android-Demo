package cc.bear3.android.demo.app

import android.app.Activity
import cc.bear3.android.demo.ui.home.HomePage
import java.lang.ref.WeakReference

/**
 *
 * @author TT
 * @since 2021-5-24
 */
object ActivityStackManager {
    val activities = mutableListOf<WeakReference<Activity>>()

    fun addActivity(activity: Activity) {
        removeInvalid()
        activities.add(WeakReference(activity))
    }

    fun removeActivity(activity: Activity) {
        val iterator = activities.iterator()

        while (iterator.hasNext()) {
            val temp = iterator.next()
            if (temp.get() == null || temp.get() == activity) {
                iterator.remove()
            }
        }
    }

    fun isAppRunning() : Boolean {
        removeInvalid()
        for (temp in activities) {
            if (temp.get() is HomePage) {
                return true
            }
        }

        return false
    }

    private fun removeInvalid() {
        val iterator = activities.iterator()

        while (iterator.hasNext()) {
            val temp = iterator.next()
            if (temp.get() == null) {
                iterator.remove()
            }
        }
    }
}