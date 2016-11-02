package br.com.sailboat.elseapp.view.drug.insert

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.common.helper.DialogHelper
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.dialog.AlarmPickerDialog
import br.com.sailboat.elseapp.view.drug.insert.presenter.InsertDrugPresenter
import kotlinx.android.synthetic.main.frag_insert_drug.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class InsertDrugFragment : BaseFragment<InsertDrugPresenter>(), InsertDrugPresenter.View {

    override val layoutId: Int get() = R.layout.frag_insert_drug
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): InsertDrugPresenter {
        return InsertDrugPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_insert_drug, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_insert_save -> {
                presenter.onClickMenuSave()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    override fun initViews() {
        initToolbar()
        bindListeners()
    }

    override fun setToolbarTitle(title: String) {
        toolbar.setTitle(title)
    }

    override fun setDrugName(name: String) {
        etInsertDrugName.setText(name)
    }

    override fun setDrugAlarm(time: String) {
        tvInsertDrugTime.setText(time)
    }

    override fun getNameFromView(): String {
        return etInsertDrugName.text.toString()
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showDialog(message: String) {
        DialogHelper.showErrorMessage(fragmentManager, message)
    }

    override fun closeActivityResultOk(drug: Drug) {
        val intent = Intent()

        ExtrasHelper.putDrug(drug, intent)

        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    override fun startAlarmChooserDialog(alarm: Calendar) {

        AlarmPickerDialog.show(fragmentManager, alarm, object : AlarmPickerDialog.Callback {

            override fun onClickOk(hourOfDay: Int, minute: Int) {
                presenter.onClickOkAlarmChooserDialog(hourOfDay, minute)
            }

        })
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun bindListeners() {
        tvInsertDrugTime.setOnClickListener {
            presenter.onClickTime()
        }

        tvInsertDrugFrequency.setOnClickListener {
            presenter.onClickFrequency()
        }

    }

}
