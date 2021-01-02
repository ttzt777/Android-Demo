package cc.bear3.android.demo.ui.util.permission

import android.Manifest
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cc.bear3.android.demo.databinding.FragmentPermissionBinding
import cc.bear3.android.demo.ui.base.BaseFragment
import cc.bear3.android.demo.ui.util.ext.onClick
import com.permissionx.guolindev.PermissionX

/**
 * Description:
 * Author: TT
 */
class PermissionFragment : BaseFragment() {
    override fun onCreateContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        val binding = FragmentPermissionBinding.inflate(inflater, container, false)

        binding.single.onClick {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .request { allGranted, _, deniedList ->
                    if (allGranted) {
                        Toast.makeText(context, "All permissions are granted", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            context,
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
                        Toast.makeText(context, "All permissions are granted", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(
                            context,
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        return binding.root
    }
}