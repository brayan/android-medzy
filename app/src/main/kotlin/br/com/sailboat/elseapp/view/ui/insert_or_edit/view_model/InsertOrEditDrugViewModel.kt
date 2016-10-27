package br.com.sailboat.elseapp.view.ui.insert_or_edit.view_model

import br.com.sailboat.elseapp.base.BaseViewModel
import br.com.sailboat.elseapp.model.Drug
import java.util.*

class InsertOrEditDrugViewModel : BaseViewModel() {

    @Transient val drugList: MutableList<Drug>

    init {
        this.drugList = ArrayList<Drug>()
    }

}
