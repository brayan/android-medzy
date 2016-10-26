package br.com.sailboat.elseapp.base

import android.content.Intent
import android.os.Bundle

open class BasePresenter {

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

    protected open fun onResumeFirstSession() {
    }

    protected open fun onResumeAfterRestart() {
    }

    protected open fun postResume() {
    }

    fun onSaveInstanceState(outState: Bundle) {
    }

    fun restoreViewModel(savedInstanceState: Bundle) {
    }

    fun extractExtrasFromIntent(intent: Intent) {
    }

}