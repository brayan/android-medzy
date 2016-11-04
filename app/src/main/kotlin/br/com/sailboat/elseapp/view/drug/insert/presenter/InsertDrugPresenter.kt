package br.com.sailboat.elseapp.view.drug.insert.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.exception.RequiredFieldNotFilledException
import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.async_task.SaveDrugAsyncTask
import br.com.sailboat.elseapp.view.drug.insert.presenter.checker.InsertDrugChecker
import br.com.sailboat.elseapp.view.drug.insert.view_model.InsertDrugViewModel
import java.util.*


class InsertDrugPresenter(view: InsertDrugPresenter.View) : BasePresenter() {

    val view: InsertDrugPresenter.View
    val viewModel: InsertDrugViewModel

    init {
        this.view = view
        this.viewModel = InsertDrugViewModel()
    }

    override fun extractExtrasFromIntent(intent: Intent) {
        val drugToEdit = ExtrasHelper.getDrug(intent)
        viewModel.drug = drugToEdit
    }

    override fun onResumeFirstSession() {

        if (isInsertingDrug()) {
            val currentTime = AlarmHelper.getTimeInitialAlarm()
            viewModel.drug = Drug(-1, "", currentTime)

            view.openKeyboard();

        } else {
            updateDrugNameView()

        }


    }

    override fun postResume() {
        updateToolbarTitle()
        updateDrugAlarmView()
    }

    fun onClickTime() {
        var alarm = drug!!.alarm
        view.startAlarmChooserDialog(alarm)
    }

    fun onClickFrequency() {

    }

    fun onClickSave() {

        try {
            collectDataFromFieldsAndBindToDrug()
            checkRequiredFields()
            save()

        } catch (e: RequiredFieldNotFilledException) {
            view.showDialog(e?.message ?: "")

        } catch (e: Exception) {
            LogHelper.printExceptionLog(e)
        }

    }

    fun onClickOkAlarmChooserDialog(hourOfDay: Int, minute: Int) {
        val alarm = drug!!.alarm
        alarm.set(Calendar.HOUR_OF_DAY, hourOfDay)
        alarm.set(Calendar.MINUTE, minute)

        updateDrugAlarmView()
    }

    private fun updateDrugAlarmView() {
        view.setDrugAlarm(AlarmHelper.formatTimeWithAndroidFormat(drug!!.alarm.time, context))
    }

    private fun updateDrugNameView() {
        view.setDrugName(drug?.name ?: "-")
    }

    private fun updateToolbarTitle() {

        if (isInsertingDrug()) {
            view.setToolbarTitle("New Drug")
        } else {
            view.setToolbarTitle("Edit Drug")
        }

    }

    private fun isInsertingDrug() : Boolean {
        return viewModel.drug == null || viewModel.drug?.id == -1L
    }
    private fun isEditingDrug() = viewModel.drug != null

    private fun collectDataFromFieldsAndBindToDrug() {
        viewModel.drug!!.name = view.getNameFromView()
    }

    private fun checkRequiredFields() {
        InsertDrugChecker().check(drug!!)
    }

    private fun save() {

        SaveDrugAsyncTask(context, drug!!, object : SaveDrugAsyncTask.Callback {

            override fun onSuccess() {
                view.closeActivityResultOk(drug!!);
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showDialog("An error occurred while saving the drug :/")
            }

        }).execute()

    }

    private val context: Context get() = view.activityContext
    private val drug: Drug? get() = viewModel.drug


    interface View {
        val activityContext: Context
        fun closeActivityResultOk(drug: Drug)
        fun getNameFromView(): String
        fun setToolbarTitle(title: String)
        fun setDrugName(name: String)
        fun setDrugAlarm(name: String)
        fun showToast(message: String)
        fun showDialog(message: String)
        fun startAlarmChooserDialog(alarm: Calendar)
        fun openKeyboard()
    }

}
