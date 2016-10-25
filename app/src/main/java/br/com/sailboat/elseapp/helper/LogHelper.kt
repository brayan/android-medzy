package br.com.sailboat.elseapp.helper

import android.util.Log

object LogHelper {

    private val TAG = "ZERO_TO_HERO_LOG"

    fun printExceptionLog(e: Exception) {
        Log.e(TAG, e.message, e)
    }
}
