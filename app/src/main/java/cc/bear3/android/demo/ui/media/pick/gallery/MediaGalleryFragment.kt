package cc.bear3.android.demo.ui.media.pick.gallery

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import cc.bear3.android.demo.databinding.FragmentMediaGalleryBinding
import cc.bear3.android.demo.ui.base.BaseFragment
import com.permissionx.guolindev.PermissionX
import timber.log.Timber

/**
 *
 * @author TT
 * @since 2021-3-8
 */
class MediaGalleryFragment : BaseFragment(), LoaderManager.LoaderCallbacks<List<MediaData>> {
    private lateinit var binding: FragmentMediaGalleryBinding

    private val adapter by lazy {
        MediaGalleryAdapter()
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMediaGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recyclerView) {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = this@MediaGalleryFragment.adapter
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
        return MediaDataLoader(requireContext())
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
}