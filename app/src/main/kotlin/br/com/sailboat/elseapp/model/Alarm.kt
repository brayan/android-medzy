package br.com.sailboat.elseapp.model

import java.io.Serializable
import java.util.*

data class Alarm(var id: Long,
                 var drugId: Long,
                 var time: Calendar,
                 var repeatType: Int) : Serializable