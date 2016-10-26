package br.com.sailboat.elseapp.helper

import android.os.Build

object ApiLevelHelper {

    fun isAtLeast(apiLevel: Int): Boolean {
        return Build.VERSION.SDK_INT >= apiLevel
    }

    fun isLowerThan(apiLevel: Int): Boolean {
        return Build.VERSION.SDK_INT < apiLevel
    }

}
