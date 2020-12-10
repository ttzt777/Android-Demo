package cc.bear3.android.demo.app

import android.app.Application
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

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}