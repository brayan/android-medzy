package br.com.sailboat.elseapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.sailboat.elseapp.R;
import br.com.sailboat.elseapp.model.Drug;
import br.com.sailboat.elseapp.view.adapter.view_holder.DrugViewHolder;


public class DrugListAdapter extends RecyclerView.Adapter<DrugViewHolder> {

    private List<Drug> drugList;
    private DrugListAdapter.Callback callback;

    public DrugListAdapter(List<Drug> items, DrugListAdapter.Callback callback) {
        setDrugList(items);
        setCallback(callback);
    }

    @Override
    public DrugViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateLayout(parent, R.layout.holder_drug);
        return new DrugViewHolder(view, getCallback());
    }

    @Override
    public void onBindViewHolder(DrugViewHolder holder, int position) {
        Drug drug = getDrugList().get(position);
        holder.onBindViewHolder(drug);
    }

    @Override
    public int getItemCount() {
        return getDrugList().size();
    }

    private View inflateLayout(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }



    public interface Callback extends DrugViewHolder.Callback {

    }
}
