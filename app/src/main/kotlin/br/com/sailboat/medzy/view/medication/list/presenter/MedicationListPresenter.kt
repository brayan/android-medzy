package br.com.sailboat.medzy.view.medication.list.presenter

import br.com.sailboat.canoe.alarm.AlarmHelper
import br.com.sailboat.canoe.base.BasePresenter
import br.com.sailboat.canoe.helper.AsyncHelper
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.canoe.recycler.RecyclerItem
import br.com.sailboat.medzy.helper.AlarmManagerHelper
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.persistence.sqlite.AlarmSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicationRecyclerItemSQLite
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite
import br.com.sailboat.medzy.view.adapter.MedicationListAdapter
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import br.com.sailboat.medzy.view.medication.list.view_model.MedicationListViewModel


class MedicationListPresenter(view: MedicationListPresenter.View) : BasePresenter<MedicationListPresenter.View>(view), MedicationListAdapter.Callback {

    private val view = view
    private val viewModel = MedicationListViewModel()

    override val meds: MutableList<RecyclerItem> get() = viewModel.medications

    override fun onResumeFirstSession() {
        loadMeds()
    }

    override fun onResumeAfterRestart() {
        updateContentViews()
    }

    override fun onClickMed(position: Int) {
        val medication = meds[position]
        view.startMedicationDetailActivity(medication as MedicationRecyclerItem)
    }

    fun onClickNewMed() {
        view.startInsertMedicationActivity()
    }

    fun onActivityResult() {
        loadMeds()
    }

    fun onSwipedMedication(position: Int) {
        try {
            val med = meds[position] as MedicationRecyclerItem
            val alarm = AlarmSQLite(view.getContext()).getAlarmById(med.alarmId)

            if (med.totalAmount <= 0 || (med.totalAmount - alarm.amount < 0)) {
                throw Exception("")
            }

            incrementAlarm(alarm)
            updateAlarm(alarm)
            setAlarmManager(alarm)
            updateTotalAmount(alarm, med)
            updateMedication(med)

            loadMeds()

        } catch (e: Exception) {
            printLogAndShowDialog(e)
        }

    }

    private fun setAlarmManager(alarm: Alarm) {
        val calendar = DateHelper.parseStringWithDatabaseFormatToCalendar(alarm.time)
        AlarmManagerHelper.setAlarm(view.getContext(), alarm.id, calendar.timeInMillis)
    }

    private fun updateAlarm(alarm: Alarm) {
        AlarmSQLite(view.getContext()).update(alarm)
    }

    private fun updateTotalAmount(alarm: Alarm, med: MedicationRecyclerItem) {
        if (med.totalAmount > 0) {
            med.totalAmount = med.totalAmount - alarm.amount
        }
    }

    private fun incrementAlarm(alarm: Alarm) {
        val calendar = DateHelper.parseStringWithDatabaseFormatToCalendar(alarm.time)
        AlarmHelper.incrementToNextValidDate(calendar, alarm.repeatType)

        alarm.time = DateHelper.parseCalendarWithDatabaseFormatToString(calendar)
    }

    private fun updateMedication(med: MedicationRecyclerItem) {
        val medicationDao = MedicationSQLite(view.getContext())
        val medication = medicationDao.getMedicationById(med.medId)

        medication.totalAmount = med.totalAmount

        medicationDao.update(medication)
    }

    private fun loadMeds() {

        AsyncHelper.execute(object : AsyncHelper.Callback {

            lateinit var meds: MutableList<MedicationRecyclerItem>

            override fun doInBackground() {
                meds = MedicationRecyclerItemSQLite(view.getContext()).getAll()
            }

            override fun onSuccess() {
                onSuccessLoadMedication(meds)
            }

            override fun onFail(e: Exception?) {
                printLogAndShowDialog(e)
            }
        })

    }

    private fun onSuccessLoadMedication(result: MutableList<MedicationRecyclerItem>) {
        meds.clear()
        meds.addAll(MedsListBuilder.buildFrom(result))

        updateContentViews()
    }

    private fun updateContentViews() {
        updateMedViews()
        updateVisibilityOfMeds()
    }

    private fun updateVisibilityOfMeds() {
        if (meds.isEmpty()) {
            view.showEmptyList()
            view.hideMeds()
        } else {
            view.showMeds()
            view.hideEmptyList()
        }
    }

    private fun updateMedViews() {
        view.updateMeds()
    }


    interface View : BasePresenter.View {
        fun updateMeds()
        fun showDialog(message: String)
        fun startInsertMedicationActivity()
        fun startMedicationDetailActivity(medication: MedicationRecyclerItem)
        fun updateMedicationRemoved(position: Int)
        fun showMeds()
        fun hideEmptyList()
        fun showEmptyList()
        fun hideMeds()
    }

}
