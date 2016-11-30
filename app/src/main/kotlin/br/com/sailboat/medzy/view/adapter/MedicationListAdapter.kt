package br.com.sailboat.medzy.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.sailboat.medzy.view.adapter.view_holder.MedicationVHModel
import br.com.sailboat.medzy.view.adapter.view_holder.MedicationViewHolder


class MedicationListAdapter(callback: Callback) : RecyclerView.Adapter<MedicationViewHolder>() {

    private val callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val view = inflateLayout(parent, MedicationViewHolder.LAYOUT_ID)
        return MedicationViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val medicine = callback.meds.get(position)
        holder.onBindViewHolder(medicine)
    }

    override fun getItemCount(): Int {
        return callback.meds.size
    }

    private fun inflateLayout(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }


    interface Callback : MedicationViewHolder.Callback {
        val meds: List<MedicationVHModel>
    }
}
