package br.com.sailboat.elseapp.base

import android.content.Context
import android.os.AsyncTask

abstract class BaseAsyncTask(val context: Context) : AsyncTask<Void, Void, Exception>() {

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


    interface Callback<T> {
        fun onSuccess(result: T?)
        fun onFail(e: Exception)
    }

}
