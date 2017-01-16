package br.com.sailboat.medzy.view.medication.detail.presenter

import android.os.Bundle
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.helper.AsyncHelper
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.canoe.helper.DecimalHelper.formatValue
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite
import br.com.sailboat.medzy.use_case.AlarmUseCase
import br.com.sailboat.medzy.view.medication.detail.view_model.MedicationDetailViewModel


class MedicationDetailPresenter(view: MedicationDetailPresenter.View) : BasePresenter<MedicationDetailPresenter.View>(view) {

    private val viewModel = MedicationDetailViewModel()

    override fun extractExtrasFromArguments(arguments: Bundle?) {
        arguments.let {
            viewModel.medId = ExtrasHelper.getMedicationId(arguments!!)
        }
    }

    override fun onResumeFirstSession() {
        loadInfo()
    }

    override fun onResumeAfterRestart() {
        updateContentViews()
    }

    fun onClickEdit() {
        view.startInsertMedicationActivity(viewModel.medId!!)
    }

    fun onClickMenuDelete() {
        deleteWorkout()
    }

    fun postActivityResult() {
        loadInfo()
    }

    private fun loadInfo() {
        loadMedication()
        loadAlarms()
    }

    private fun loadMedication() {
        AsyncHelper.execute(object : AsyncHelper.Callback {

            lateinit var medication: Medication

            override fun doInBackground() {
                medication = MedicationSQLite(view.getContext()).getMedicationById(viewModel.medId!!)
            }

            override fun onSuccess() {
                viewModel.medication = medication
                view.setMedicationName(medication.name)
                view.setTotalAmount(formatValue(medication.totalAmount, 2))
            }

            override fun onFail(e: Exception?) {
                printLogAndShowDialog(e)
            }
        })
    }

    private fun loadAlarms() {
        AsyncHelper.execute(object : AsyncHelper.Callback {

            lateinit var alarms: MutableList<Alarm>

            override fun doInBackground() {
                alarms = AlarmUseCase.getAlarms(view.getContext(), viewModel.medId!!)
            }

            override fun onSuccess() {
                viewModel.alarms.clear()
                viewModel.alarms.addAll(alarms)

                updateMedicationAlarmView()
            }

            override fun onFail(e: Exception?) {
                printLogAndShowDialog(e)
            }
        })

    }

    private fun deleteWorkout() {
        AsyncHelper.execute(object : AsyncHelper.Callback {

            override fun doInBackground() {
                cancelAlarms()
                deleteMedication()
                deleteAlarms()
            }

            override fun onSuccess() {
                view.closeActivityResultOk()
            }

            override fun onFail(e: Exception?) {
                printLogAndShowDialog(e)
            }
        })
    }

    private fun cancelAlarms() {
        val alarms = AlarmSQLite(view.getContext()).getAlarmsByMed(viewModel.medId!!)
        AlarmManagerHelper.cancelAlarms(view.getContext(), alarms)
    }

    private fun deleteMedication() {
        MedicationSQLite(view.getContext()).delete(viewModel.medId!!)
    }

    private fun deleteAlarms() {
        AlarmSQLite(view.getContext()).deleteAllByMed(viewModel.medId!!)
    }

    private fun updateContentViews() {
        updateMedNameView()
        updateMedicationAlarmView()
    }

    private fun updateMedNameView() {
        view.setMedicationName(viewModel.medication!!.name)
    }

    private fun updateMedicationAlarmView() {
//        view.setAlarm(AlarmHelper.formatTimeWithAndroidFormat(medication!!.alarm.time, context))
        // TODO: JUST FOR TESTS
        val alarm = DateHelper.parseStringWithDatabaseFormatToCalendar(viewModel.alarms[0].time)
        view.setAlarmTime(DateHelper.formatTimeWithAndroidFormat(view.getContext(), alarm))
        view.setTotalAmount(formatValue(viewModel.medication!!.totalAmount, 2))
        view.setAlarmAmount(formatValue(viewModel.alarms[0].amount, 2))
    }


    interface View : BasePresenter.View {
        fun setMedicationName(name: String)
        fun setTotalAmount(total: String)
        fun setAlarmTime(time: String)
        fun setAlarmAmount(amount: String)
        fun closeActivityResultOk()
        fun startInsertMedicationActivity(medicationId: Long)
    }

}
