package br.com.sailboat.medzy.view.medicine.insert.view_model

import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medicine
import java.io.Serializable
import java.util.*

class InsertMedicineViewModel : Serializable {

    var medicineId: Long? = -1
    var medicine: Medicine? = null
    @Transient var alarms = ArrayList<Alarm>()

}
