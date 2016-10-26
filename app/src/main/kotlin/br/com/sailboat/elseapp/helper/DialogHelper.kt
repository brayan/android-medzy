package br.com.sailboat.elseapp.helper

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog

class DialogHelper : DialogFragment() {

    var message: String? = null
    var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton(android.R.string.ok, null)

        return dialog.create()
    }

    override fun onDestroyView() {
        if (dialog != null && retainInstance) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }

    companion object {

        private val TAG = "TAG_DIALOG_HELPER"

        fun showMessage(manager: FragmentManager, message: String) {
            showMessage(manager, message, null)
        }

        fun showMessage(manager: FragmentManager, msg: String, title: String?) {
            val dialog = DialogHelper()
            dialog.title = title
            dialog.message = msg

            dialog.show(manager, TAG)
        }
    }
}
