package br.com.sailboat.medzy.view.medication.insert

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.view.inputmethod.InputMethodManager
import br.com.sailboat.canoe.base.BaseFragment
import br.com.sailboat.canoe.dialog.InputDoubleDialog
import br.com.sailboat.canoe.dialog.TimePickerCanoeDialog
import br.com.sailboat.canoe.helper.DecimalHelper
import br.com.sailboat.canoe.helper.DialogHelper
import br.com.sailboat.canoe.helper.InputFilterDecimalDigits
import br.com.sailboat.canoe.helper.UIHelper
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.view.medication.insert.presenter.InsertMedicationPresenter
import kotlinx.android.synthetic.main.frag_insert_medication.*
import kotlinx.android.synthetic.main.med_content.*
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
        inflater?.inflate(R.menu.menu_insert_medication, menu)
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

    override fun setMedName(name: String) {
        etInsertMedicationName.setText(name)
    }

    override fun putCursorAtTheEnd() {
        etInsertMedicationName.setSelection(etInsertMedicationName.length())
    }

    override fun hideKeyboard() {
        UIHelper.hideKeyboard(activity)
    }

    override fun setMedTotalAmount(totalAmount: Double) {
        tvMedContentTotalAmount.setText(DecimalHelper.formatWithTwoDecimals(totalAmount))
    }

    override fun setAlarm(time: String) {
        tvMedContentAlarmTime.setText(time)
    }

    override fun setAlarmAmount(amount: Double) {
        tvMedContentAlarmAmount.setText(DecimalHelper.formatWithTwoDecimals(amount))
    }

    override fun setAlarmsView(alarms: MutableList<Alarm>) {
        // TODO: GENERATE ALARM VIEWS
    }

    override fun getMedName(): String {
        return etInsertMedicationName.text.toString()
    }

    override fun getTotalAmount(): String {
        return tvMedContentTotalAmount.text.toString()
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

        TimePickerCanoeDialog.show(fragmentManager, alarm, object : TimePickerCanoeDialog.Callback {

            override fun onClickOk(hourOfDay: Int, minute: Int) {
                presenter.onClickOkAlarmChooserDialog(-1L, hourOfDay, minute)
            }

        })
    }

    override fun showAmountInputDialog(position: Int, amount: Double) {
        val dialog = InputDoubleDialog()
        dialog.appendTitle(getString(R.string.amount))
        dialog.appendValue(amount)
        dialog.appendInputFilter(InputFilterDecimalDigits(6, 2))
        dialog.build(fragmentManager, object : InputDoubleDialog.Callback{

            override fun onClickOk(input: Double) {
                presenter.onClickOkAmountInputDialog(position, input)
            }
        })

    }

    override fun showTotalAmountInputDialog(totalAmount: Double) {
        val dialog = InputDoubleDialog()
        dialog.appendTitle(getString(R.string.amount))
        dialog.appendValue(totalAmount)
        dialog.appendInputFilter(InputFilterDecimalDigits(6, 2))
        dialog.build(fragmentManager, object : InputDoubleDialog.Callback{

            override fun onClickOk(input: Double) {
                presenter.onClickOkTotalAmountInputDialog(input)
            }
        })
    }

    override fun openKeyboard() {
        etInsertMedicationName.requestFocus()

        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(etInsertMedicationName, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false);
    }

    private fun bindListeners() {
        frameTotalAmount.setOnClickListener {
            presenter.onClickTotalAmount()
        }

        frameAlarmTime.setOnClickListener {
            presenter.onClickTime()
        }

        frameAlarmAmount.setOnClickListener {
            presenter.onClickAmount()
        }

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }

        etInsertMedicationName.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    UIHelper.hideKeyboard(activity)
                    return true
                }
                return false
            }


        })

    }

}
