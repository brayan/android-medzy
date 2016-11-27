package br.com.sailboat.medzy.view.medicine.insert.presenter.checker

import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException
import br.com.sailboat.medzy.model.Medicine

class InsertMedicineChecker {

    fun check(medicine: Medicine) {
        checkName(medicine)
    }

    private fun checkName(medicine: Medicine) {

        if (medicine.name?.trim().isNullOrEmpty()) {
            throw RequiredFieldNotFilledException("You must enter a name for the medicine")
        }

    }
}