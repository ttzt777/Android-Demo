package cc.bear3.android.demo.ui.demo.video

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.android.demo.databinding.PageVideoDemoBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.util.context.startWithAnim

/**
 *
 * @author TT
 * @since 2021-4-25
 */
class VideoDemoPage : BaseActivity() {
    private lateinit var binding: PageVideoDemoBinding

    override fun initView(savedInstanceState: Bundle?) {
        binding.playerView.start()
    }

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = PageVideoDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, VideoDemoPage::class.java)
            context.startWithAnim(intent)
        }
    }
}