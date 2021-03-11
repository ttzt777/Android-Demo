package cc.bear3.android.demo.ui.media.pick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.FragmentMediaPickBinding
import cc.bear3.android.demo.ui.base.BaseFragment
import cc.bear3.android.demo.ui.util.ext.onClick

/**
 *
 * @author TT
 * @since 2021-3-4
 */
class MediaPickFragment : BaseFragment() {
    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentMediaPickBinding.inflate(inflater, container, false)

        binding.openGallery.onClick {
            findNavController().navigate(R.id.mediagallery_fragment)
        }

        return binding.root
    }
}