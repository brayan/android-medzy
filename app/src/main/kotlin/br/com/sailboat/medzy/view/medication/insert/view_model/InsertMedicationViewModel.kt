package br.com.sailboat.medzy.view.medication.insert.view_model

import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import java.io.Serializable
import java.util.*

class InsertMedicationViewModel : Serializable {

    var medicationId: Long? = -1
    var medication: Medication? = null
    @Transient var alarms = ArrayList<Alarm>()

}
