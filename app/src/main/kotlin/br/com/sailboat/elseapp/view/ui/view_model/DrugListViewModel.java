package br.com.sailboat.elseapp.view.ui.view_model;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.elseapp.base.BaseViewModel;
import br.com.sailboat.elseapp.model.Drug;

public class DrugListViewModel extends BaseViewModel {

    private transient final List<Drug> drugList;

    public DrugListViewModel() {
        this.drugList = new ArrayList<>();
    }

    public List<Drug> getDrugList() {
        return drugList;
    }

}
