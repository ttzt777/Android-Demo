package cc.bear3.android.demo.ui.demo.video.core

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import cc.bear3.android.demo.BuildConfig
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util


/**
 *
 * @author TT
 * @since 2021-4-25
 */
class VideoPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val playerProxy : IExoPlayerProxy

    init {
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val rendererController = DefaultVideoRendererController(context)
        playerProxy = DefaultVideoPlayerProxy(context, rendererController)

        val viewController = DefaultVideoPlayerViewController(context, playerProxy)
        playerProxy.viewController = viewController

        addView(rendererController.textureView, layoutParams)
        addView(viewController.getControllerView(LayoutInflater.from(context), this), layoutParams)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    open fun start() {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, BuildConfig.APPLICATION_ID)
        )
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse("https://media.w3.org/2010/05/sintel/trailer.mp4"))
        playerProxy.prepare(videoSource)
        playerProxy.play()
    }
}