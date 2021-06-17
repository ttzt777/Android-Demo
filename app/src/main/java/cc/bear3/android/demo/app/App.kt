package cc.bear3.android.demo.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cc.bear3.android.demo.BuildConfig
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
    }
}