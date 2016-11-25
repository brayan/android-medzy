package br.com.sailboat.elseapp.view.medicine.insert.presenter.checker

import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.canoe.exception.RequiredFieldNotFilledException

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