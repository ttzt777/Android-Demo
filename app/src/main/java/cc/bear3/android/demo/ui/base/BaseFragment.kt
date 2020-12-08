package cc.bear3.android.demo.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 *
 * @author TT
 * @since 2020-12-4
 */
abstract class BaseFragment : Fragment() {
    protected fun getAvailableArgs(savedInstanceState: Bundle?, block: (bundle: Bundle) -> Unit){
        val bundle = savedInstanceState
            ?: if (arguments != null) {
                arguments
            } else {
                null
            }

        bundle?.let {
            block(it)
        }
    }
}