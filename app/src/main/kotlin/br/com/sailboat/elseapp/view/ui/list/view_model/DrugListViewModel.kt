package br.com.sailboat.elseapp.view.ui.list.view_model

import br.com.sailboat.elseapp.base.BaseViewModel
import br.com.sailboat.elseapp.model.Drug
import java.util.*

class DrugListViewModel : BaseViewModel() {

    @Transient val drugList: MutableList<Drug>

    init {
        this.drugList = ArrayList<Drug>()
    }

}
