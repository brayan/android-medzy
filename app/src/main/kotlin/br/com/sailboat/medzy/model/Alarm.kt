package br.com.sailboat.medzy.model

import java.io.Serializable
import java.util.*

data class Alarm(var id: Long,
                 var medicineId: Long,
                 var time: Calendar,
                 var repeatType: Int) : Serializable