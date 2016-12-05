package br.com.sailboat.medzy.model

import java.io.Serializable

data class Medication(var id: Long, var name: String, var totalAmount: Double) : Serializable
