package cc.bear3.android.demo.ui.media.pick.gallery

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.loader.content.AsyncTaskLoader

/**
 *
 * @author TT
 * @since 2021-3-4
 */
private const val ORDER_BY = MediaStore.MediaColumns.DATE_MODIFIED + " DESC"

private val PROJECTION_MEDIA = arrayOf(
    MediaStore.MediaColumns._ID,
    MediaStore.MediaColumns.DISPLAY_NAME,
    MediaStore.MediaColumns.TITLE,
    MediaStore.MediaColumns.MIME_TYPE,
    MediaStore.MediaColumns.DATE_ADDED,
    MediaStore.MediaColumns.DATE_MODIFIED,
    MediaStore.MediaColumns.WIDTH,
    MediaStore.MediaColumns.HEIGHT,
    MediaStore.MediaColumns.SIZE,
    MediaStore.MediaColumns.DURATION
)

class MediaDataLoader(context: Context) : AsyncTaskLoader<List<MediaData>>(context) {
    private var cached: List<MediaData>? = null
    private var observerRegistered = false
    private val forceLoadContentObserver = ForceLoadContentObserver()

    override fun deliverResult(data: List<MediaData>?) {
        if (!isReset && isStarted) {
            super.deliverResult(data)
        }
    }

    override fun onStartLoading() {
        cached?.let {
            deliverResult(it)
        }

        if (takeContentChanged() || cached == null) {
            forceLoad()
        }

        registerContentObserver()
    }

    override fun onStopLoading() {
        cancelLoad()
    }

    override fun onReset() {
        super.onReset()

        onStopLoading()
        cached = null
        unregisterContentObserver()
    }

    override fun onAbandon() {
        super.onAbandon()
        unregisterContentObserver()
    }

    override fun loadInBackground(): List<MediaData> {
        val resultList = mutableListOf<MediaData>()
        resultList.addAll(queryImages())
        resultList.addAll(queryVideos())
        resultList.addAll(queryAudios())

        resultList.sortWith(Comparator { o1, o2 -> o2.dateModified.compareTo(o1.dateModified) })

        return resultList
    }

    private fun queryImages(): List<MediaData> {
        return query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            PROJECTION_MEDIA,
            MediaData.Type.Image
        )
    }

    private fun queryVideos(): List<MediaData> {
        return query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            PROJECTION_MEDIA,
            MediaData.Type.Video
        )
    }

    private fun queryAudios(): List<MediaData> {
        return query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            PROJECTION_MEDIA,
            MediaData.Type.Audio
        )
    }

    private fun query(
        contentUri: Uri,
        projection: Array<String>,
        type: MediaData.Type
    ): List<MediaData> {
        val dataList = mutableListOf<MediaData>()

        val cursor = context.contentResolver.query(
            contentUri,
            projection,
            null,
            null,
            ORDER_BY
        ) ?: return dataList

        cursor.use {
            while (cursor.moveToNext()) {
                val id = it.getLong(it.getColumnIndex(MediaStore.MediaColumns._ID))
                val name = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                val title = it.getString(it.getColumnIndex(MediaStore.MediaColumns.TITLE))
                val mimeType = it.getString(it.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE))
                val dateAdded = it.getLong(it.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED))
                val dateModified =
                    it.getLong(it.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED))
                val width = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.WIDTH))
                val height = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.HEIGHT))
                val size = it.getInt(it.getColumnIndex(MediaStore.MediaColumns.SIZE))

                val data = MediaData(
                    id,
                    name,
                    title,
                    Uri.withAppendedPath(contentUri, id.toString()),
                    mimeType,
                    dateAdded,
                    dateModified,
                    width,
                    height,
                    size,
                    type
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && (type == MediaData.Type.Video || type == MediaData.Type.Audio)) {
                    val duration =
                        it.getInt(cursor.getColumnIndex(MediaStore.MediaColumns.DURATION))
                    data.duration = duration
                }

                dataList.add(data)
            }
        }

        return dataList
    }

    private fun registerContentObserver() {
        if (!observerRegistered) {
            val cr = context.contentResolver
            cr.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, forceLoadContentObserver
            )
            cr.registerContentObserver(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, false, forceLoadContentObserver
            )
            observerRegistered = true
        }
    }

    private fun unregisterContentObserver() {
        if (observerRegistered) {
            observerRegistered = false
            context.contentResolver.unregisterContentObserver(forceLoadContentObserver)
        }
    }
}