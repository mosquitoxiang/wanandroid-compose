package com.illu.baselibrary.utils

import android.util.Log

object LogUtil {

    fun d(message: String) {
        Log.d(TAG, message)
    }

    private val TAG = "APP_LOG"
}