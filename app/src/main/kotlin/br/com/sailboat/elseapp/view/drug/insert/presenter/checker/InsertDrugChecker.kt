package br.com.sailboat.elseapp.view.drug.insert.presenter.checker

import br.com.sailboat.elseapp.common.exception.RequiredFieldNotFilledException
import br.com.sailboat.elseapp.model.Drug

class InsertDrugChecker {

    fun check(drug: Drug) {
        checkName(drug)
    }

    private fun checkName(drug: Drug) {

        if (drug.name?.trim().isNullOrEmpty()) {
            throw RequiredFieldNotFilledException("You must enter a name for the drug")
        }

    }
}