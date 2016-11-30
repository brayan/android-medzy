package br.com.sailboat.medzy.helper

import android.content.Intent

object ExtrasHelper {

    private val MEDICATION_ID = "MEDICATION_ID"
    private val ALARM_ID = "ALARM_ID"

    fun putMedicationId(id: Long, intent: Intent) {
        intent.putExtra(MEDICATION_ID, id)
    }

    fun getMedicationId(intent: Intent): Long? {
        return intent.getLongExtra(MEDICATION_ID, -1L)
    }

    fun putAlarmId(id: Long, intent: Intent) {
        intent.putExtra(ALARM_ID, id)
    }

    fun getAlarmId(intent: Intent): Long? {
        return intent.getLongExtra(ALARM_ID, -1L)
    }

}
