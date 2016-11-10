package br.com.sailboat.elseapp.view.medicine.list.view_model

import br.com.sailboat.elseapp.base.BaseViewModel
import br.com.sailboat.elseapp.model.MedicineVHModel
import java.util.*

class MedicineListViewModel : BaseViewModel() {

    @Transient
    val medicines: MutableList<MedicineVHModel>  = ArrayList<MedicineVHModel>()

}
