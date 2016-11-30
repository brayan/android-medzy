package br.com.sailboat.medzy.view.medication.insert.presenter

import android.content.Context
import android.content.Intent
import br.com.sailboat.canoe.alarm.RepeatType
import br.com.sailboat.canoe.async.callback.OnSuccess
import br.com.sailboat.canoe.async.callback.OnSuccessWithResult
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.canoe.helper.SafeOperation
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.view.async_task.AsyncLoadAlarms
import br.com.sailboat.medzy.view.async_task.AsyncLoadMedication
import br.com.sailboat.medzy.view.async_task.AsyncSaveMedicationAndAlarms
import br.com.sailboat.medzy.view.medication.insert.presenter.checker.InsertMedicationChecker
import br.com.sailboat.medzy.view.medication.insert.view_model.InsertMedicationViewModel
import java.util.*


class InsertMedicationPresenter(view: InsertMedicationPresenter.View) : BasePresenter() {

    private val view = view
    private val viewModel = InsertMedicationViewModel()
    private val alarms: MutableList<Alarm> get() = viewModel.alarms

    override fun extractExtrasFromIntent(intent: Intent) {
        val medicineId = ExtrasHelper.getMedicationId(intent)
        viewModel.medicationId = medicineId
    }

    override fun onResumeFirstSession() {

        if (isInsertingMedicine()) {
            viewModel.medication = Medication(-1, "")
            // TODO: JUST FOR TESTS
            viewModel.alarms.add(Alarm(-1, -1, DateHelper.getInitialDateTime(), RepeatType.DAY))
            view.openKeyboard()
            updateMedicineAlarmView()

        } else {
            loadInfo()
        }

    }

    override fun postResume() {
//        updateMedicineAlarmView()
    }

    fun onClickTime() {
        // TODO: JUST FOR TESTS
        var alarm = viewModel.alarms.get(0)
        view.startAlarmChooserDialog(alarm.time)
    }

    fun onClickFrequency() {

    }

    fun onClickSave() {

        SafeOperation.withDialog(view.getContext()) {
            collectDataFromFieldsAndBindToMedicine()
            prepareAlarms()
            checkRequiredFields()
            save()
        }

    }

    private fun prepareAlarms() {
        for (alarm in alarms) {
            if (DateHelper.isBeforeNow(alarm.time)) {
                alarm.time.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
    }

    fun onClickOkAlarmChooserDialog(alarmId: Long, hourOfDay: Int, minute: Int) {
        // TODO: JUST FOR TESTS
        val currentTime = Calendar.getInstance()
        currentTime.set(Calendar.SECOND, 0)
        currentTime.set(Calendar.MILLISECOND, 0)

        val alarm = viewModel.alarms[0]
        alarm.time.time = currentTime.time
        alarm.time.set(Calendar.HOUR_OF_DAY, hourOfDay)
        alarm.time.set(Calendar.MINUTE, minute)

        updateMedicineAlarmView()
    }

    private fun loadInfo() {
        loadMedicine()
        loadAlarms()
    }

    private fun loadMedicine() {
        AsyncLoadMedication.load(view.getContext(), viewModel.medicationId!!, object : OnSuccessWithResult<Medication> {

            override fun onSuccess(med: Medication) {
                viewModel.medication = med
                updateMedicineNameView()
            }

        })
    }

    private fun loadAlarms() {
        AsyncLoadAlarms.load(view.getContext(), viewModel.medicationId!!, object : OnSuccessWithResult<MutableList<Alarm>> {

            override fun onSuccess(list: MutableList<Alarm>) {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(list)

                view.setAlarmsView(viewModel.alarms)

                updateMedicineAlarmView()
            }

        })
    }

    private fun updateMedicineAlarmView() {
        // TODO: JUST FOR TESTS
        view.setAlarm(DateHelper.formatTimeWithAndroidFormat(view.getContext(), viewModel.alarms[0].time))
    }

    private fun updateMedicineNameView() {
        view.setMedicineName(viewModel.medication?.name ?: "-")
        view.putCursorAtTheEnd()
    }


    private fun isInsertingMedicine(): Boolean {
        return viewModel.medicationId == -1L
    }

    private fun collectDataFromFieldsAndBindToMedicine() {
        viewModel.medication!!.name = view.getMedicineNameFromView()
    }

    private fun checkRequiredFields() {
        InsertMedicationChecker().check(view.getContext(), viewModel.medication!!, viewModel.alarms)
    }

    private fun save() {

        AsyncSaveMedicationAndAlarms.save(view.getContext(), viewModel.medication!!, alarms, OnSuccess {
            view.closeActivityResultOk()
        })

    }


    interface View {
        fun getContext(): Context
        fun closeActivityResultOk()
        fun getMedicineNameFromView(): String
        fun setMedicineName(name: String)
        fun setAlarm(time: String)
        fun setAlarmsView(alarms: MutableList<Alarm>)
        fun showInfoMessage(message: String)
        fun showErrorMessage(message: String)
        fun startAlarmChooserDialog(alarm: Calendar)
        fun openKeyboard()
        fun putCursorAtTheEnd()
    }

}
