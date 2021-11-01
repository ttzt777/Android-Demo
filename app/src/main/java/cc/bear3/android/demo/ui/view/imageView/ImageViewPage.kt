package cc.bear3.android.demo.ui.view.imageView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cc.bear3.android.demo.R
import cc.bear3.android.demo.databinding.PageImageviewBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim
import com.bumptech.glide.Glide

/**
 *
 * @author TT
 * @since 2021-6-2
 */
class ImageViewPage : BaseActivity<PageImageviewBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        Glide.with(this).load(R.mipmap.image_1).into(binding.local1)
        Glide.with(this).load(R.mipmap.image_2).into(binding.local2)
        Glide.with(this).load(R.mipmap.image_3).into(binding.local3)

        Glide.with(this).load("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4").into(binding.network1)
        Glide.with(this).load("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319222227698228.mp4").into(binding.network2)
        Glide.with(this).load("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4").into(binding.network3)
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, ImageViewPage::class.java)
            context.startWithAnim(intent)
        }
    }
}