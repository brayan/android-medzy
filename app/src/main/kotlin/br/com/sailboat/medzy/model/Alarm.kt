package br.com.sailboat.medzy.model

import java.io.Serializable

data class Alarm(var id: Long,
                 var medId: Long,
                 var time: String,
                 var repeatType: Int,
                 var amount: Double) : Serializable