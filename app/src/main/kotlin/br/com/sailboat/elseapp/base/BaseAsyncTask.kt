package br.com.sailboat.elseapp.base

import android.os.AsyncTask

abstract class BaseAsyncTask : AsyncTask<Void, Void, Exception>() {

    override fun doInBackground(vararg params: Void): Exception? {
        try {
            onDoInBackground()
            return null
        } catch (e: Exception) {
            return e
        }

    }

    override fun onPostExecute(e: Exception?) {
        if (e == null) {
            onSuccess()
        } else {
            onFail(e)
        }
    }

    protected abstract fun onDoInBackground()

    protected abstract fun onSuccess()

    protected abstract fun onFail(e: Exception)

}
