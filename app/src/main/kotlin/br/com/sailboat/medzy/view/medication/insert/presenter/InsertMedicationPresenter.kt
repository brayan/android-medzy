package br.com.sailboat.medzy.view.medication.insert.presenter

import android.content.Intent
import br.com.sailboat.canoe.alarm.RepeatType
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException
import br.com.sailboat.canoe.helper.AsyncHelper
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite
import br.com.sailboat.medzy.use_case.AlarmUseCase
import br.com.sailboat.medzy.use_case.MedicationUseCase
import br.com.sailboat.medzy.view.medication.insert.presenter.checker.InsertMedicationChecker
import br.com.sailboat.medzy.view.medication.insert.view_model.InsertMedicationViewModel
import java.util.*


class InsertMedicationPresenter(view: InsertMedicationPresenter.View) : BasePresenter<InsertMedicationPresenter.View>(view) {

    private val view = view
    private val viewModel = InsertMedicationViewModel()
    private val alarms: MutableList<Alarm> get() = viewModel.alarms

    override fun extractExtrasFromIntent(intent: Intent) {
        val medId = ExtrasHelper.getMedicationId(intent)
        viewModel.medicationId = medId
    }

    override fun onResumeFirstSession() {

        if (isInsertingMed()) {
            viewModel.medication = Medication(-1, "", 0.0)
            // TODO: JUST FOR TESTS
            viewModel.alarms.add(Alarm(-1, -1, DateHelper.parseCalendarWithDatabaseFormatToString(DateHelper.getInitialDateTime()), RepeatType.DAY, 1.0))
            view.openKeyboard()
            updateMedAlarmView()
            updateMedTotalAmountView()

        } else {
            loadInfo()
        }

    }

    override fun onResumeAfterRestart() {
        updateMedAlarmView()
    }

    fun onClickTime() {
        // TODO: JUST FOR TESTS
        view.hideKeyboard();
        var alarm = viewModel.alarms.get(0)
        view.startAlarmChooserDialog(DateHelper.parseStringWithDatabaseFormatToCalendar(alarm.time))
    }

    fun onClickAmount() {
        var alarm = viewModel.alarms.get(0)

        val position = 0
        view.showAmountInputDialog(position, alarm.amount)
    }

    fun onClickTotalAmount() {
        view.showTotalAmountInputDialog(viewModel.medication!!.totalAmount)
    }

    fun onClickSave() {

        try {
            collectDataFromFieldsAndBindToMed()
            prepareAlarms()
            checkRequiredFields()
            save()

        } catch (e: RequiredFieldNotFilledException) {
            showMessage(e.message)

        } catch (e: Exception) {
            printLogAndShowDialog(e)
        }
    }

    private fun prepareAlarms() {
        for (alarm in alarms) {
            val calendar = DateHelper.parseStringWithDatabaseFormatToCalendar(alarm.time)

            if (DateHelper.isBeforeNow(calendar)) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                alarm.time = DateHelper.parseCalendarWithDatabaseFormatToString(calendar)
            }
        }
    }

    fun onClickOkAlarmChooserDialog(hourOfDay: Int, minute: Int) {
        // TODO: JUST FOR TESTS
        val currentTime = Calendar.getInstance()
        currentTime.set(Calendar.SECOND, 0)
        currentTime.set(Calendar.MILLISECOND, 0)
        currentTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
        currentTime.set(Calendar.MINUTE, minute)


        val alarm = viewModel.alarms[0]
        alarm.time = DateHelper.parseCalendarWithDatabaseFormatToString(currentTime)

        updateMedAlarmView()
    }

    private fun loadInfo() {
        loadMed()
        loadAlarms()
    }

    fun onClickOkAmountInputDialog(position: Int, input: Double) {
        val alarm = alarms[position]
        alarm.amount = roundValue(input, 2)

        updateMedAlarmView()
    }

    fun onClickOkTotalAmountInputDialog(totalAmount: Double) {
        viewModel.medication!!.totalAmount = roundValue(totalAmount, 2)
        updateMedTotalAmountView()
    }


    private fun loadMed() {
        AsyncHelper.execute(object : AsyncHelper.Callback {

            lateinit var medication: Medication

            override fun doInBackground() {
                medication = MedicationSQLite(view.getContext()).getMedicationById(viewModel.medicationId!!)
            }

            override fun onSuccess() {
                viewModel.medication = medication
                updateMedNameView()
                updateMedTotalAmountView()
            }

            override fun onFail(e: Exception?) {
                printLogAndShowDialog(e)
            }
        })
    }

    private fun updateMedTotalAmountView() {
        view.setMedTotalAmount(formatValue(viewModel.medication?.totalAmount!!, 2) ?: "0")
    }

    private fun loadAlarms() {
        AsyncHelper.execute(object : AsyncHelper.Callback {

            lateinit var alarms: MutableList<Alarm>

            override fun doInBackground() {
                alarms = AlarmUseCase.getAlarms(view.getContext(), viewModel.medicationId!!)
            }

            override fun onSuccess() {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(alarms)

                view.setAlarmsView(viewModel.alarms)

                updateMedAlarmView()
            }

            override fun onFail(e: Exception?) {
                printLogAndShowDialog(e)
            }
        })

    }

    private fun updateMedAlarmView() {
        // TODO: JUST FOR TESTS
        view.setAlarm(DateHelper.formatTimeWithAndroidFormat(getContext(), DateHelper.parseStringWithDatabaseFormatToCalendar(viewModel.alarms[0].time)))
        view.setAlarmAmount(formatValue(viewModel.alarms[0].amount, 2))
    }

    private fun updateMedNameView() {
        view.setMedName(viewModel.medication?.name ?: "-")
        view.putCursorAtTheEnd()
    }


    private fun isInsertingMed(): Boolean {
        return viewModel.medicationId == -1L
    }

    private fun collectDataFromFieldsAndBindToMed() {
        viewModel.medication!!.name = view.getMedName()
        collectTotalAmount()
    }

    private fun collectTotalAmount() {
        try {
            viewModel.medication!!.totalAmount = view.getTotalAmount().toDouble()
        } catch (e: Exception) {
            viewModel.medication!!.totalAmount = 0.0
        }

    }

    private fun checkRequiredFields() {
        InsertMedicationChecker().check(view.getContext(), viewModel.medication!!, viewModel.alarms)
    }

    private fun save() {
        AsyncHelper.execute(object : AsyncHelper.Callback {

            override fun doInBackground() {
                cancelAndDeleteAlarms()
                saveOrUpdateMed()
                saveAlarms()
            }

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception?) {
                printLogAndShowDialog(e)
            }
        })

    }

    private fun saveOrUpdateMed() {
        MedicationUseCase.saveOrUpdateMed(view.getContext(), viewModel.medication!!)
    }

    private fun saveAlarms() {
        AlarmUseCase.saveAndSetAlarms(view.getContext(), viewModel.medication!!.id, alarms)
    }

    private fun cancelAndDeleteAlarms() {

        if (MedicationUseCase.isMedNotNew(viewModel.medication)) {
            AlarmUseCase.cancelAlarms(view.getContext(), viewModel.medication!!.id)
            AlarmUseCase.deleteAlarms(view.getContext(), viewModel.medication!!.id)
        }

    }


    interface View : BasePresenter.View {
        fun closeActivityResultOk()
        fun getMedName(): String
        fun getTotalAmount(): String
        fun setMedName(name: String)
        fun setMedTotalAmount(totalAmount: String)
        fun setAlarm(time: String)
        fun setAlarmAmount(amount: String)
        fun setAlarmsView(alarms: MutableList<Alarm>)
        fun showInfoMessage(message: String)
        fun showErrorMessage(message: String)
        fun startAlarmChooserDialog(alarm: Calendar)
        fun hideKeyboard()
        fun showAmountInputDialog(position: Int, amount: Double)
        fun showTotalAmountInputDialog(totalAmount: Double)
        fun openKeyboard()
        fun putCursorAtTheEnd()
    }

}
