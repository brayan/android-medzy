package br.com.sailboat.elseapp.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<Presenter : BasePresenter> : Fragment() {

    abstract var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
        presenter = newPresenterInstance()
        checkStateAndRestoreViewModel(savedInstanceState)
        extractExtrasFromIntent(activity.intent)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(layoutId, container, false)
        initViews(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            Activity.RESULT_OK -> {
                onActivityResultOk(requestCode, data)
                return
            }
            Activity.RESULT_CANCELED -> {
                onActivityResultCanceled(requestCode, data)
                return
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    private fun checkStateAndRestoreViewModel(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter.restoreViewModel(savedInstanceState)
        }
    }

    private fun extractExtrasFromIntent(intent: Intent) {
        presenter.extractExtrasFromIntent(intent)
    }

    protected abstract fun newPresenterInstance(): Presenter

    protected abstract val layoutId: Int

    protected abstract fun initViews(view: View)

    protected fun onActivityResultOk(requestCode: Int, data: Intent) {
    }

    protected fun onActivityResultCanceled(requestCode: Int, data: Intent) {
    }
}
