package br.com.sailboat.medzy.view.medicine.detail.view_model

import br.com.sailboat.medzy.model.Alarm
import java.io.Serializable

class MedicineDetailViewModel : Serializable {

    var medicineId: Long? = -1L
    var medicineName: String? =  ""
    var alarms: MutableList<Alarm>? = null

}
