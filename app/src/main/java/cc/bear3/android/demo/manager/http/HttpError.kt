package cc.bear3.android.demo.manager.http

import android.content.Context
import cc.bear3.android.demo.R

/**
 * Description:
 * Author: TT
 * From: 2019/8/14
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/14
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/14
 * *-*
 */
enum class HttpError(var message: String?) {
    Success(null),
    Default(null),
    NetWork(null) {
        override fun getDefaultMessage(context: Context?): String? {
            return context?.resources?.getString(R.string.http_network)
        }
    },
    Server(null) {
        override fun getDefaultMessage(context: Context?): String? {
            return context?.resources?.getString(R.string.http_server)
        }
    },
    Data(null) {
        override fun getDefaultMessage(context: Context?): String? {
            return context?.resources?.getString(R.string.http_data)
        }
    },
    Timeout(null) {
        override fun getDefaultMessage(context: Context?): String? {
            return context?.resources?.getString(R.string.http_timeout)
        }
    },
    Permission(null) {
        override fun getDefaultMessage(context: Context?): String? {
            return context?.resources?.getString(R.string.http_permission)
        }
    };

    open fun getDefaultMessage(context: Context?): String? {
        return null
    }
}