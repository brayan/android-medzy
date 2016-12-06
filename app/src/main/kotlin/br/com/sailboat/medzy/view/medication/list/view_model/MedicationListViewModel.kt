package br.com.sailboat.medzy.view.medication.list.view_model

import br.com.sailboat.canoe.recycler.RecyclerItem
import java.io.Serializable
import java.util.*

class MedicationListViewModel : Serializable {

    @Transient
    val medications: MutableList<RecyclerItem> = ArrayList<RecyclerItem>()

}
