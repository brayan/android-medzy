package br.com.sailboat.medzy.view.medicine.insert.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.helper.LogHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medicine
import br.com.sailboat.canoe.alarm.RepeatType
import br.com.sailboat.canoe.async.SimpleAsyncTask
import br.com.sailboat.canoe.async.callback.ResultCallback
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.view.async_task.AsyncLoadAlarms
import br.com.sailboat.medzy.view.async_task.AsyncSaveMedicineAndAlarms
import br.com.sailboat.medzy.view.medicine.insert.presenter.checker.InsertMedicineChecker
import br.com.sailboat.medzy.view.medicine.insert.view_model.InsertMedicineViewModel
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
            viewModel.alarms.add(Alarm(-1, -1, DateHelper.getInitialDateTime(), RepeatType.DAY))
            view.openKeyboard()

        } else {
//            updateMedicineNameView()
            // TODO: LOAD MEDICINE
            loadAlarms()
        }

    }

    override fun postResume() {
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
        view.setAlarm(DateHelper.formatTimeWithAndroidFormat(viewModel.alarms[0].time.time, view.getContext()))
    }

    private fun updateMedicineNameView() {
        view.setMedicineName(viewModel.medicine?.name ?: "-")
        view.putCursorAtTheEnd()
    }

    private fun loadAlarms() {
        AsyncLoadAlarms.load(view.getContext(), viewModel.medicineId!!, object : ResultCallback<MutableList<Alarm>> {

            override fun onSuccess(list: MutableList<Alarm>) {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(list)

                view.setAlarmsView(viewModel.alarms)
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showErrorMessage("An error occurred while loading the alarms :/")
            }

        })
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

        AsyncSaveMedicineAndAlarms.save(view.getContext(), viewModel.medicine!!, alarms, object : SimpleAsyncTask.Callback {

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showErrorMessage("An error occurred while saving the medicine :/")
            }

        })

    }


    interface View {
        fun getContext() : Context
        fun closeActivityResultOk()
        fun getMedicineNameFromView(): String
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
