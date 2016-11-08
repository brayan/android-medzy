package br.com.sailboat.elseapp.model

import java.io.Serializable
import java.util.*

data class MedicineVHModel(var medicineId: Long, var medicineName: String, var alarmTime: Calendar)