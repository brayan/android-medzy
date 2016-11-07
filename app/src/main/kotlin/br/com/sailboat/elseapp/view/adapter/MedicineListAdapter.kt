package br.com.sailboat.elseapp.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.view.adapter.view_holder.MedicineViewHolder


class MedicineListAdapter(items: List<MedicineVHModel>, callback: MedicineListAdapter.Callback) : RecyclerView.Adapter<MedicineViewHolder>() {

    private var medicineList: List<MedicineVHModel>
    private var callback: Callback

    init {
        this.medicineList = items
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = inflateLayout(parent, R.layout.holder_medicine)
        return MedicineViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicineList.get(position)
        holder.onBindViewHolder(medicine)
    }

    override fun getItemCount(): Int {
        return medicineList.size
    }

    private fun inflateLayout(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }


    interface Callback : MedicineViewHolder.Callback
}
