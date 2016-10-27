package br.com.sailboat.elseapp.helper

import android.content.Intent

import br.com.sailboat.elseapp.model.Drug

object ExtrasHelper {

    private val DRUG = "DRUG"
    private val DELETE_DRUG = "DELETE_DRUG"

    fun putDrug(drug: Drug, intent: Intent) {
        intent.putExtra(DRUG, drug)
    }

    fun getDrug(intent: Intent): Drug {
        return intent.getSerializableExtra(DRUG) as Drug
    }

    fun deleteDrug(intent: Intent) {
        intent.putExtra(DELETE_DRUG, true)
    }

    fun hasDrugToDelete(intent: Intent): Boolean {
        return intent.hasExtra(DELETE_DRUG)
    }

}
