package cc.bear3.android.demo.ui.media.pick

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cc.bear3.android.demo.databinding.PageMediaPickBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.media.pick.gallery.MediaGalleryPage
import cc.bear3.android.demo.ui.util.ext.onClick
import cc.bear3.android.demo.util.context.startWithAnim

/**
 *
 * @author TT
 * @since 2021-3-4
 */
class MediaPickPage : BaseActivity<PageMediaPickBinding>(PageMediaPickBinding::inflate) {
    override fun initView(savedInstanceState: Bundle?) {
        binding.openGallery.onClick {
            MediaGalleryPage.invoke(this)
        }
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, MediaPickPage::class.java)
            context.startWithAnim(intent)
        }
    }
}