package br.com.sailboat.elseapp.base

import android.content.Intent
import android.os.Bundle

abstract class BasePresenter {

    var isFirstSession = true

    fun onResume() {
        checkFirstSessionAndResume()
        postResume()
    }

    private fun checkFirstSessionAndResume() {
        if (isFirstSession) {
            onResumeFirstSession()
            isFirstSession = false
        } else {
            onResumeAfterRestart()
        }
    }

    protected fun onResumeFirstSession() {
    }

    protected fun onResumeAfterRestart() {
    }

    protected fun postResume() {
    }

    fun onSaveInstanceState(outState: Bundle) {
    }

    fun restoreViewModel(savedInstanceState: Bundle) {
    }

    fun extractExtrasFromIntent(intent: Intent) {
    }

}