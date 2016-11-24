package br.com.sailboat.elseapp.view.medicine.list.view_model

import br.com.sailboat.elseapp.model.MedicineVHModel
import java.io.Serializable
import java.util.*

class MedicineListViewModel : Serializable {

    @Transient
    val medicines: MutableList<MedicineVHModel>  = ArrayList<MedicineVHModel>()

}
