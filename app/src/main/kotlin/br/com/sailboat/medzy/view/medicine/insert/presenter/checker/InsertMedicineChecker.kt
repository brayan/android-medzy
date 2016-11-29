package br.com.sailboat.medzy.view.medicine.insert.presenter.checker

import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException
import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medicine
import java.util.*

class InsertMedicineChecker {

    fun check(medicine: Medicine, alarms: ArrayList<Alarm>) {
        checkName(medicine)
        checkAlarms(alarms)
    }

    private fun checkAlarms(alarms: ArrayList<Alarm>) {
        if (alarms.isEmpty()) {
            throw RequiredFieldNotFilledException("You must add at least one alarm")
        }
    }

    private fun checkName(medicine: Medicine) {

        if (medicine.name?.trim().isNullOrEmpty()) {
            throw RequiredFieldNotFilledException("You must enter a name for the medicine")
        }

    }
}