package br.com.sailboat.elseapp.model

import java.io.Serializable
import java.util.*

data class MedicineVHModel(var id: Long, var name: String, var alarm: Calendar) : Serializable