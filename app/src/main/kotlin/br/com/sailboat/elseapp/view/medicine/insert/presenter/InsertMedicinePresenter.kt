package br.com.sailboat.elseapp.view.medicine.insert.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.exception.RequiredFieldNotFilledException
import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.view.async_task.LoadAlarmsAsyncTask
import br.com.sailboat.elseapp.view.async_task.SaveMedicineAndAlarmsAsyncTask
import br.com.sailboat.elseapp.view.medicine.insert.presenter.checker.InsertMedicineChecker
import br.com.sailboat.elseapp.view.medicine.insert.view_model.InsertMedicineViewModel
import java.util.*


class InsertMedicinePresenter(view: InsertMedicinePresenter.View) : BasePresenter() {

    val view: InsertMedicinePresenter.View
    val viewModel: InsertMedicineViewModel

    init {
        this.view = view
        this.viewModel = InsertMedicineViewModel()
    }

    override fun extractExtrasFromIntent(intent: Intent) {
        val medicineId = ExtrasHelper.getMedicineId(intent)
        viewModel.medicineId = medicineId
    }

    override fun onResumeFirstSession() {

        if (isInsertingDrug()) {
            viewModel.medicine = Medicine(-1, "")
            // TODO: JUST FOR TESTS
            viewModel.alarms.add(Alarm(-1, -1, AlarmHelper.getTimeInitialAlarm(), 0))
            view.openKeyboard()

        } else {
//            updateMedicineNameView()
            // TODO: LOAD MEDICINE
            loadAlarms()
        }


    }

    override fun postResume() {
        updateToolbarTitle()
        updateDrugAlarmView()
    }

    fun onClickTime() {
        // TODO: JUST FOR TESTS
        var alarm = viewModel.alarms.get(0)
        view.startAlarmChooserDialog(alarm.time)
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

    fun onClickOkAlarmChooserDialog(alarmId: Long, hourOfDay: Int, minute: Int) {
        // TODO: JUST FOR TESTS
        val alarm = viewModel.alarms[0]
        alarm.time.set(Calendar.HOUR_OF_DAY, hourOfDay)
        alarm.time.set(Calendar.MINUTE, minute)

        updateDrugAlarmView()
    }

    private fun updateDrugAlarmView() {
//        view.setDrugAlarm(AlarmHelper.formatTimeWithAndroidFormat(medicine!!.alarm.time, context))
        // TODO: JUST FOR TESTS
        view.setDrugAlarm(AlarmHelper.formatTimeWithAndroidFormat(viewModel.alarms[0].time.time, context))
    }

    private fun updateMedicineNameView() {
        view.setDrugName(medicine?.name ?: "-")
        view.putCursorAtTheEnd()
    }

    private fun loadAlarms() {
        LoadAlarmsAsyncTask(context, viewModel.medicineId!!, object : LoadAlarmsAsyncTask.Callback {

            override fun onSucess(list: MutableList<Alarm>) {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(list)

                view.setAlarmsView(viewModel.alarms)
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showDialog("An error occurred while loading the alarms :/")
            }


        }).execute()
    }

    private fun updateToolbarTitle() {

        if (isInsertingDrug()) {
            view.setToolbarTitle("New Drug")
        } else {
            view.setToolbarTitle("Edit Drug")
        }

    }

    private fun isInsertingDrug() : Boolean {
        return viewModel.medicine == null || viewModel.medicine?.id == -1L
    }
    private fun isEditingDrug() = viewModel.medicine != null

    private fun collectDataFromFieldsAndBindToDrug() {
        viewModel.medicine!!.name = view.getNameFromView()
    }

    private fun checkRequiredFields() {
        InsertMedicineChecker().check(medicine!!)
    }

    private fun save() {

        SaveMedicineAndAlarmsAsyncTask(context, medicine!!, alarms, object : SaveMedicineAndAlarmsAsyncTask.Callback {

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showDialog("An error occurred while saving the medicine :/")
            }

        }).execute()

    }

    private val context: Context get() = view.activityContext
    private val medicine: Medicine? get() = viewModel.medicine
    private val alarms: MutableList<Alarm> get() = viewModel.alarms


    interface View {
        val activityContext: Context
        fun closeActivityResultOk()
        fun getNameFromView(): String
        fun setToolbarTitle(title: String)
        fun setDrugName(name: String)
        fun setDrugAlarm(name: String)
        fun setAlarmsView(alarms: MutableList<Alarm>)
        fun showToast(message: String)
        fun showDialog(message: String)
        fun startAlarmChooserDialog(alarm: Calendar)
        fun openKeyboard()
        fun putCursorAtTheEnd()
    }

}
