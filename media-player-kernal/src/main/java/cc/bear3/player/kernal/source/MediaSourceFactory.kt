package cc.bear3.player.kernal.source

import android.content.Context
import cc.bear3.player.kernal.manager.PlayerProtocolManager
import com.google.android.exoplayer2.MediaItem
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
        fun createMediaSource(
            context: Context,
            url: String,
            dataSourceFactory: DataSource.Factory = createDefaultDataSource(context)
        ): MediaSource {
            return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url))
        }

        private fun createDefaultDataSource(context: Context): DataSource.Factory {
            return DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, PlayerProtocolManager.protocol.getApplicationId())
            )
        }
    }
}