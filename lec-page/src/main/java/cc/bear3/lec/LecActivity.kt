package cc.bear3.lec

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

/**
 *
 * @author TT
 * @since 2020-12-8
 */
abstract class LecActivity : AppCompatActivity(), ILecPage {

    override lateinit var root: FrameLayout

    override var loadingView: View? = null
    override var errorView: View? = null

    override val params = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    override val state = MutableLiveData(LecState.Content)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_lec)
        root = findViewById(R.id.fl_layout_lec_root)

        params.topMargin = 0
        root.addView(onCreateContentView(), params)

        defaultObserverLecState()
    }

    override fun getLifecycleOwner(): LifecycleOwner {
        return this
    }
}