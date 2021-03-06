package cc.bear3.android.demo.ui.demo.video.player.core.source

import android.content.Context
import cc.bear3.android.demo.BuildConfig
import cc.bear3.android.demo.app.App
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 *
 * @author TT
 * @since 2021-6-4
 */
class MediaSourceFactory {
    companion object {
        fun createLoopMediaSource(
            url: String,
            dataSourceFactory: DataSource.Factory = createDefaultDataSource(App.sContext)
        ): MediaSource {
            return LoopingMediaSource(createMediaSource(url, dataSourceFactory))
        }

        fun createMediaSource(
            url: String,
            dataSourceFactory: DataSource.Factory = createDefaultDataSource(App.sContext)
        ): MediaSource {
            return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url))
        }

        fun createDefaultDataSource(context: Context): DataSource.Factory {
            return DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, BuildConfig.APPLICATION_ID)
            )
        }
    }
}