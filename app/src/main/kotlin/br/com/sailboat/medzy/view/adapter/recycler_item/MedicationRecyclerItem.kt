package br.com.sailboat.medzy.view.adapter.recycler_item

import br.com.sailboat.canoe.recycler.RecyclerItem
import br.com.sailboat.medzy.view.adapter.ViewType

data class MedicationRecyclerItem(var medId: Long,
                                  var alarmId: Long,
                                  var medName: String,
                                  var alarmTime: String,
                                  var totalAmount: Double,
                                  var amount: Double) : RecyclerItem {

    override fun getViewType(): Int {
        return ViewType.MEDICATION
    }

}