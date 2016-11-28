package br.com.sailboat.medzy.view.medicine.detail.view_model

import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medicine
import java.io.Serializable
import java.util.*

class MedicineDetailViewModel : Serializable {

    var medicineId: Long? = -1L
    var medicine: Medicine? = null
    var alarms: MutableList<Alarm> = ArrayList<Alarm>()

}
