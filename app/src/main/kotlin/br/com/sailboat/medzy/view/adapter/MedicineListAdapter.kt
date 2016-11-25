package br.com.sailboat.medzy.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineViewHolder


class MedicineListAdapter(callback: Callback) : RecyclerView.Adapter<MedicineViewHolder>() {

    private val callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = inflateLayout(parent, MedicineViewHolder.LAYOUT_ID)
        return MedicineViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = callback.meds.get(position)
        holder.onBindViewHolder(medicine)
    }

    override fun getItemCount(): Int {
        return callback.meds.size
    }

    private fun inflateLayout(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }


    interface Callback : MedicineViewHolder.Callback {
        val meds: List<MedicineVHModel>
    }
}
