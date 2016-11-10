package br.com.sailboat.elseapp.view.medicine.insert.view_model

import br.com.sailboat.elseapp.base.BaseViewModel
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.model.Medicine
import java.util.*

class InsertMedicineViewModel : BaseViewModel() {

    var medicineId: Long? = -1
    var medicine: Medicine? = null
    @Transient var alarms = ArrayList<Alarm>()

}
