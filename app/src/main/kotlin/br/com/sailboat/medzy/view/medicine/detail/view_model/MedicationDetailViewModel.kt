package br.com.sailboat.medzy.view.medicine.detail.view_model

import br.com.sailboat.medzy.model.Alarm
import br.com.sailboat.medzy.model.Medication
import java.io.Serializable
import java.util.*

class MedicationDetailViewModel : Serializable {

    var medId: Long? = -1L
    var medication: Medication? = null
    var alarms: MutableList<Alarm> = ArrayList<Alarm>()

}
