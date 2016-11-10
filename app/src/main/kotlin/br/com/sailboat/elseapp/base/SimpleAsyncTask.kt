package br.com.sailboat.elseapp.base

import android.content.Context
import android.os.AsyncTask

abstract class SimpleAsyncTask (val context: Context, val callback: Callback) : AsyncTask<Void, Void, Exception>() {

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
            callback.onSuccess()
        } else {
            callback.onFail(e)
        }
    }

    protected abstract fun onDoInBackground()


    interface Callback {
        fun onSuccess()
        fun onFail(e: Exception)
    }

}