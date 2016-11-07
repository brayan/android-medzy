package br.com.sailboat.elseapp.common.helper

import android.content.Intent
import br.com.sailboat.elseapp.model.Medicine

object ExtrasHelper {

    private val MEDICINE = "MEDICINE"

    fun putMedicine(medicine: Medicine, intent: Intent) {
        intent.putExtra(MEDICINE, medicine)
    }

    fun getMedicine(intent: Intent): Medicine? {
        return intent.getSerializableExtra(MEDICINE) as? Medicine
    }

}
