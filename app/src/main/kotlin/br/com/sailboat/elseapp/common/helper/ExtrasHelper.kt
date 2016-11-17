package br.com.sailboat.elseapp.common.helper

import android.content.Intent

object ExtrasHelper {

    private val MEDICINE_NAME = "MEDICINE_NAME"
    private val MEDICINE_ID = "MEDICINE_ID"
    private val ALARM_ID = "ALARM_ID"

    fun putMedicineName(name: String, intent: Intent) {
        intent.putExtra(MEDICINE_NAME, name)
    }

    fun getMedicineName(intent: Intent): String? {
        return intent.getStringExtra(MEDICINE_NAME)
    }

    fun putMedicineId(id: Long, intent: Intent) {
        intent.putExtra(MEDICINE_ID, id)
    }

    fun getMedicineId(intent: Intent): Long? {
        return intent.getLongExtra(MEDICINE_ID, -1L)
    }

    fun putAlarmId(id: Long, intent: Intent) {
        intent.putExtra(ALARM_ID, id)
    }

    fun getAlarmId(intent: Intent): Long? {
        return intent.getLongExtra(ALARM_ID, -1L)
    }

}
