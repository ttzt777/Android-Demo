package cc.bear3.android.demo.ui.base

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import cc.bear3.android.demo.R

/**
 *
 * @author TT
 * @since 2021-3-18
 */
abstract class BaseDialogFragment : DialogFragment() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStyle()
    }

    fun show(fragmentManager: FragmentManager) {
        try {
            val c =
                Class.forName("androidx.fragment.app.DialogFragment")
            val con = c.getConstructor()
            val obj = con.newInstance()
            val dismissed = c.getDeclaredField("mDismissed")
            dismissed.isAccessible = true
            dismissed[obj] = false
            val shownByMe = c.getDeclaredField("mShownByMe")
            shownByMe.isAccessible = true
            shownByMe[obj] = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.add(this, javaClass.simpleName)
        ft.commitAllowingStateLoss()
    }

    override fun dismiss() {
        super.dismissAllowingStateLoss()
    }

    protected open fun initStyle() {
        setStyle(STYLE_NORMAL, R.style.BaseDialog)
    }
}