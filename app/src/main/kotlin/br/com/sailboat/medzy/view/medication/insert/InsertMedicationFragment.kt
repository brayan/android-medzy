package br.com.sailboat.medzy.view.medication.insert

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.view.inputmethod.InputMethodManager
import br.com.sailboat.canoe.base.BaseFragment
import br.com.sailboat.canoe.helper.DialogHelper
import br.com.sailboat.canoe.helper.UIHelper
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.view.dialog.AlarmPickerDialog
import br.com.sailboat.medzy.view.medication.insert.presenter.InsertMedicationPresenter
import kotlinx.android.synthetic.main.alarm.*
import kotlinx.android.synthetic.main.frag_insert_medication.*
import java.util.*

class InsertMedicationFragment : BaseFragment<InsertMedicationPresenter>(), InsertMedicationPresenter.View {

    private lateinit var toolbar: Toolbar

    override fun getLayoutId(): Int {
        return R.layout.frag_insert_medication
    }

    override fun newPresenterInstance(): InsertMedicationPresenter {
        return InsertMedicationPresenter(this)
    }

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

    override fun initViews(view: View) {
        initToolbar(view)
        bindListeners()
    }

    override fun setMedicineName(name: String) {
        etInsertMedicineName.setText(name)
    }

    override fun putCursorAtTheEnd() {
        etInsertMedicineName.setSelection(etInsertMedicineName.length())
    }

    override fun setAlarm(time: String) {
        tvAlarmTime.setText(time)
    }

    override fun setAlarmsView(alarms: MutableList<Alarm>) {
        // TODO: GENERATE ALARM VIEWS
    }

    override fun getMedicineNameFromView(): String {
        return etInsertMedicineName.text.toString()
    }

    override fun showInfoMessage(message: String) {
        DialogHelper.showMessage(activity, message, null)
    }

    override fun showErrorMessage(message: String) {
        DialogHelper.showMessage(activity, message, null)
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

    private fun initToolbar(view: View) {
        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false);
    }

    private fun bindListeners() {
        frameAlarmTime.setOnClickListener {
            presenter.onClickTime()
        }

        tvInsertMedicationFrequency.setOnClickListener {
            presenter.onClickFrequency()
        }

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }

        etInsertMedicineName.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    UIHelper.hideKeyboard(activity, etInsertMedicineName)
                    return true
                }
                return false
            }


        })

    }

}
