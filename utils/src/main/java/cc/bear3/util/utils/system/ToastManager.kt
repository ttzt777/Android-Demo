package cc.bear3.util.utils.system

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 *
 * @author TT
 * @since 2021-3-1
 */
object ToastManager : IToast {
    var toast: IToast = SystemToast()

    override fun show(context: Context?, resId: Int) {
        toast.show(context, resId)
    }

    override fun show(context: Context?, string: String) {
        toast.show(context, string)
    }
}

interface IToast {
    fun show(context: Context?, @StringRes resId: Int)

    fun show(context: Context?, string: String)
}

class SystemToast : IToast {
    override fun show(context: Context?, resId: Int) {
        val string = context?.resources?.getString(resId) ?: return

        show(context, string)
    }

    override fun show(context: Context?, string: String) {
        val appCtx = context?.applicationContext ?: return

        Toast.makeText(appCtx, string, Toast.LENGTH_SHORT).show()
    }
}