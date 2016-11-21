package br.com.sailboat.elseapp.common.helper

import android.R
import android.content.Context
import android.support.v7.app.AlertDialog

class DialogHelper  {

    companion object {

        fun showMessage(context: Context, message: String?) {
            val alert = AlertDialog.Builder(context)
            alert.setMessage(message)
            alert.setPositiveButton(R.string.ok, null)
            alert.create().show()
        }

    }

}
