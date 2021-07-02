package cc.bear3.android.demo.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cc.bear3.android.demo.BuildConfig
import cc.bear3.android.demo.ui.view.smartRefreshLayout.footer.RefreshLayoutFooter
import cc.bear3.android.demo.ui.view.smartRefreshLayout.header.RefreshLayoutHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2020-12-4
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        sContext = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var sContext: Context
            private set

        init {
            SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
                //全局设置（优先级最低）
                with(layout) {
                    setEnableOverScrollDrag(false)
                    setEnableOverScrollBounce(true)
                    setEnableLoadMoreWhenContentNotFull(true)
                    setEnableScrollContentWhenRefreshed(true)
                    setEnableScrollContentWhenLoaded(true)
                    setEnableHeaderTranslationContent(true)
                    setReboundDuration(500)
                    setHeaderHeight(48.toFloat())
                    setFooterHeight(48.toFloat())
                    setFooterMaxDragRate(1.5f)
                    setDragRate(0.75f)
                    setEnableAutoLoadMore(true)
                }
            }

            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
                RefreshLayoutHeader(context)
            }

            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                RefreshLayoutFooter(context)
            }
        }
    }
}