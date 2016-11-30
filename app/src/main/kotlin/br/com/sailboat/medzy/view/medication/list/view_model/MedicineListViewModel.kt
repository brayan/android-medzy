package br.com.sailboat.medzy.view.medication.list.view_model

import br.com.sailboat.medzy.view.adapter.view_holder.MedicationVHModel
import java.io.Serializable
import java.util.*

class MedicineListViewModel : Serializable {

    @Transient
    val medications: MutableList<MedicationVHModel>  = ArrayList<MedicationVHModel>()

}
