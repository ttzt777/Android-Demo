package cc.bear3.player.core.source

import android.content.Context
import cc.bear3.player.core.manager.PlayerProtocolManager
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
            context: Context,
            url: String,
            dataSourceFactory: DataSource.Factory = createDefaultDataSource(context)
        ): MediaSource {
            return LoopingMediaSource(createMediaSource(context, url, dataSourceFactory))
        }

        fun createMediaSource(
            context: Context,
            url: String,
            dataSourceFactory: DataSource.Factory = createDefaultDataSource(context)
        ): MediaSource {
            return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url))
        }

        fun createDefaultDataSource(context: Context): DataSource.Factory {
            return DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, PlayerProtocolManager.protocol.getApplicationId())
            )
        }
    }
}