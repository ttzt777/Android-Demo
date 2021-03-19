package cc.bear3.android.demo.ui.media.pick.gallery

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import cc.bear3.android.demo.databinding.PageMediaGalleryBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim
import com.permissionx.guolindev.PermissionX
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-3-8
 */
class MediaGalleryPage : BaseActivity(), LoaderManager.LoaderCallbacks<List<MediaData>> {
    private lateinit var binding: PageMediaGalleryBinding

    private val adapter by lazy {
        MediaGalleryAdapter()
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = PageMediaGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        with(binding.recyclerView) {
            layoutManager = GridLayoutManager(this@MediaGalleryPage, 4)
            adapter = this@MediaGalleryPage.adapter
        }

        PermissionX.init(this)
            .permissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .request { allGranted, _, _ ->
                if (allGranted) {
                    LoaderManager.getInstance(this).initLoader(0, null, this)
                } else {

                }
            }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<MediaData>> {
        return MediaDataLoader(this)
    }

    override fun onLoadFinished(loader: Loader<List<MediaData>>, data: List<MediaData>?) {
        adapter.dataRefresh(data)
        data?.let {
            for (temp in data) {
                Timber.d(temp.toString())
            }
        }
    }

    override fun onLoaderReset(loader: Loader<List<MediaData>>) {

    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, MediaGalleryPage::class.java)
            context.startWithAnim(intent)
        }
    }
}