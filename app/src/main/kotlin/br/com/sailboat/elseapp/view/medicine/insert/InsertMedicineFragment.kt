package br.com.sailboat.elseapp.view.medicine.insert

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.common.helper.DialogHelper
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.view.dialog.AlarmPickerDialog
import br.com.sailboat.elseapp.view.medicine.insert.presenter.InsertMedicinePresenter
import kotlinx.android.synthetic.main.frag_insert_medicine.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class InsertMedicineFragment : BaseFragment<InsertMedicinePresenter>(), InsertMedicinePresenter.View {

    override val LAYOUT_ID: Int get() = R.layout.frag_insert_medicine

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_insert_medicine, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_item_save -> {
                presenter.onClickSave()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    override fun newPresenterInstance(): InsertMedicinePresenter {
        return InsertMedicinePresenter(this)
    }

    override fun initViews() {
        initToolbar()
        bindListeners()
    }

    override fun setToolbarTitle(title: String) {
        toolbar.setTitle(title)
    }

    override fun setMedicineName(name: String) {
        etInsertMedicineName.setText(name)
    }

    override fun putCursorAtTheEnd() {
        etInsertMedicineName.setSelection(etInsertMedicineName.length())
    }

    override fun setAlarm(time: String) {
        tvInsertMedicineTime.setText(time)
    }

    override fun setAlarmsView(alarms: MutableList<Alarm>) {
        // TODO: GENERATE ALARM VIEWS
    }

    override fun getNameFromView(): String {
        return etInsertMedicineName.text.toString()
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        DialogHelper.showErrorMessage(fragmentManager, message)
    }

    override fun closeActivityResultOk() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun startAlarmChooserDialog(alarm: Calendar) {

        AlarmPickerDialog.show(fragmentManager, alarm, object : AlarmPickerDialog.Callback {

            override fun onClickOk(hourOfDay: Int, minute: Int) {
                presenter.onClickOkAlarmChooserDialog(-1L, hourOfDay, minute)
            }

        })
    }

    override fun openKeyboard() {
        etInsertMedicineName.requestFocus()

        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etInsertMedicineName, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
    }

    private fun bindListeners() {
        tvInsertMedicineTime.setOnClickListener {
            presenter.onClickTime()
        }

        tvInsertMedicineFrequency.setOnClickListener {
            presenter.onClickFrequency()
        }

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }

    }

}
