package br.com.sailboat.medzy.view.medicine.list.view_model

import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel
import java.io.Serializable
import java.util.*

class MedicineListViewModel : Serializable {

    @Transient
    val medicines: MutableList<MedicineVHModel>  = ArrayList<MedicineVHModel>()

}
