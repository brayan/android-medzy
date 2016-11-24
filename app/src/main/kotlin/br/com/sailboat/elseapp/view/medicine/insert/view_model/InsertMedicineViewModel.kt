package br.com.sailboat.elseapp.view.medicine.insert.view_model

import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.model.Medicine
import java.io.Serializable
import java.util.*

class InsertMedicineViewModel : Serializable {

    var medicineId: Long? = -1
    var medicine: Medicine? = null
    @Transient var alarms = ArrayList<Alarm>()

}
