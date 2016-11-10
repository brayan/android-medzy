package br.com.sailboat.elseapp.view.medicine.insert.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.base.SimpleAsyncTask
import br.com.sailboat.elseapp.common.exception.RequiredFieldNotFilledException
import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.model.RepeatType
import br.com.sailboat.elseapp.view.async_task.LoadAlarmsAsyncTask
import br.com.sailboat.elseapp.view.async_task.SaveMedicineAndAlarmsAsyncTask
import br.com.sailboat.elseapp.view.medicine.insert.presenter.checker.InsertMedicineChecker
import br.com.sailboat.elseapp.view.medicine.insert.view_model.InsertMedicineViewModel
import java.util.*


class InsertMedicinePresenter(view: InsertMedicinePresenter.View) : BasePresenter() {

    private val view = view
    private val viewModel = InsertMedicineViewModel()
    private val alarms: MutableList<Alarm> get() = viewModel.alarms

    override fun extractExtrasFromIntent(intent: Intent) {
        val medicineId = ExtrasHelper.getMedicineId(intent)
        viewModel.medicineId = medicineId
    }

    override fun onResumeFirstSession() {

        if (isInsertingMedicine()) {
            viewModel.medicine = Medicine(-1, "")
            // TODO: JUST FOR TESTS
            viewModel.alarms.add(Alarm(-1, -1, AlarmHelper.getTimeInitialAlarm(), RepeatType.DAY))
            view.openKeyboard()

        } else {
//            updateMedicineNameView()
            // TODO: LOAD MEDICINE
            loadAlarms()
        }

    }

    override fun postResume() {
        updateToolbarTitle()
        updateMedicineAlarmView()
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
            collectDataFromFieldsAndBindToMedicine()
            checkRequiredFields()
            save()

        } catch (e: RequiredFieldNotFilledException) {
            view.showInfoMessage(e?.message ?: "")

        } catch (e: Exception) {
            LogHelper.printExceptionLog(e)
        }

    }

    fun onClickOkAlarmChooserDialog(alarmId: Long, hourOfDay: Int, minute: Int) {
        // TODO: JUST FOR TESTS
        val alarm = viewModel.alarms[0]
        alarm.time.set(Calendar.HOUR_OF_DAY, hourOfDay)
        alarm.time.set(Calendar.MINUTE, minute)

        updateMedicineAlarmView()
    }

    private fun updateMedicineAlarmView() {
//        view.setAlarm(AlarmHelper.formatTimeWithAndroidFormat(medicine!!.alarm.time, context))
        // TODO: JUST FOR TESTS
        view.setAlarm(AlarmHelper.formatTimeWithAndroidFormat(viewModel.alarms[0].time.time, view.getContext()))
    }

    private fun updateMedicineNameView() {
        view.setMedicineName(viewModel.medicine?.name ?: "-")
        view.putCursorAtTheEnd()
    }

    private fun loadAlarms() {
        LoadAlarmsAsyncTask(view.getContext(), viewModel.medicineId!!, object : BaseAsyncTask.Callback<MutableList<Alarm>> {

            override fun onSuccess(list: MutableList<Alarm>) {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(list)

                view.setAlarmsView(viewModel.alarms)
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showErrorMessage("An error occurred while loading the alarms :/")
            }


        }).execute()
    }

    private fun updateToolbarTitle() {

        if (isInsertingMedicine()) {
            view.setToolbarTitle("New Medicine")
        } else {
            view.setToolbarTitle("Edit Medicine")
        }

    }

    private fun isInsertingMedicine() : Boolean {
        return viewModel.medicine == null || viewModel.medicine?.id == -1L
    }

    private fun collectDataFromFieldsAndBindToMedicine() {
        viewModel.medicine!!.name = view.getMedicineNameFromView()
    }

    private fun checkRequiredFields() {
        InsertMedicineChecker().check(viewModel.medicine!!)
    }

    private fun save() {

        SaveMedicineAndAlarmsAsyncTask(view.getContext(), viewModel.medicine!!, alarms, object : SimpleAsyncTask.Callback {

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showErrorMessage("An error occurred while saving the medicine :/")
            }

        }).execute()

    }


    interface View {
        fun getContext() : Context
        fun closeActivityResultOk()
        fun getMedicineNameFromView(): String
        fun setToolbarTitle(title: String)
        fun setMedicineName(name: String)
        fun setAlarm(name: String)
        fun setAlarmsView(alarms: MutableList<Alarm>)
        fun showInfoMessage(message: String)
        fun showErrorMessage(message: String)
        fun startAlarmChooserDialog(alarm: Calendar)
        fun openKeyboard()
        fun putCursorAtTheEnd()
    }

}
