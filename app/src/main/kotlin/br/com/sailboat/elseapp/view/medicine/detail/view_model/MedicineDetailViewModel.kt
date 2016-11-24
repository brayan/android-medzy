package br.com.sailboat.elseapp.view.medicine.detail.view_model

import br.com.sailboat.elseapp.model.Alarm
import java.io.Serializable

class MedicineDetailViewModel : Serializable {

    var medicineId: Long? = -1L
    var medicineName: String? =  ""
    var alarms: MutableList<Alarm>? = null

}
