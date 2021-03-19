package cc.bear3.android.demo.ui.util.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cc.bear3.android.demo.databinding.PagePermissionBinding
import cc.bear3.android.demo.ui.base.BaseActivity
import cc.bear3.android.demo.ui.util.ext.onClick
import cc.bear3.android.demo.util.context.startWithAnim
import com.permissionx.guolindev.PermissionX

/**
 * Description:
 * Author: TT
 */
class PermissionPage : BaseActivity() {
    private lateinit var binding: PagePermissionBinding

    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = PagePermissionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.single.onClick {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            this,
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        binding.multi.onClick {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_CONTACTS
                )
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            this,
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    companion object {
        fun invoke(context: Context) {
            val intent = Intent(context, PermissionPage::class.java)
            context.startWithAnim(intent)
        }
    }
}