package br.com.sailboat.medzy.view.medicine.insert.presenter.checker

import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import java.util.*

class InsertMedicationChecker {

    fun check(medication: Medication, alarms: ArrayList<Alarm>) {
        checkName(medication)
        checkAlarms(alarms)
    }

    private fun checkAlarms(alarms: ArrayList<Alarm>) {
        if (alarms.isEmpty()) {
            throw RequiredFieldNotFilledException("You must add at least one alarm")
        }
    }

    private fun checkName(medication: Medication) {

        if (medication.name?.trim().isNullOrEmpty()) {
            throw RequiredFieldNotFilledException("You must enter a name for the medication")
        }

    }
}