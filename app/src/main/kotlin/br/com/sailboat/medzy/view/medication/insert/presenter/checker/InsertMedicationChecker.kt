package br.com.sailboat.medzy.view.medication.insert.presenter.checker

import android.content.Context
import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import java.util.*

class InsertMedicationChecker {

    fun check(context: Context, medication: Medication, alarms: ArrayList<Alarm>) {
        checkName(context, medication)
        checkAlarms(context, alarms)
    }

    private fun checkAlarms(context: Context, alarms: ArrayList<Alarm>) {
        if (alarms.isEmpty()) {
            throw RequiredFieldNotFilledException(context.getString(R.string.check_add_alarm))
        }
    }

    private fun checkName(context: Context, medication: Medication) {

        if (medication.name.trim().isNullOrEmpty()) {
            throw RequiredFieldNotFilledException(context.getString(R.string.check_enter_name))
        }

    }
}