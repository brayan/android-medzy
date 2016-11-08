package br.com.sailboat.elseapp.view.medicine.detail.view_model

import br.com.sailboat.elseapp.base.BaseViewModel
import br.com.sailboat.elseapp.model.Alarm
import br.com.sailboat.elseapp.model.Medicine

class MedicineDetailViewModel : BaseViewModel() {

    var medicineId: Long? = -1L
    var medicineName: String? =  ""
    var alarms: MutableList<Alarm>? = null

}
