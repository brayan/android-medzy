package br.com.sailboat.elseapp.view.adapter.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.sailboat.elseapp.model.Drug
import kotlinx.android.synthetic.main.holder_drug.view.*

class DrugViewHolder(itemView: View, callback: DrugViewHolder.Callback) : RecyclerView.ViewHolder(itemView) {

    private val callback: Callback

    init {
        this.callback = callback
        bindListeners()
    }

    fun onBindViewHolder(drug: Drug) {
        itemView.tvHolderDrugName.text = drug.name
        itemView.tvHolderDrugAlarmTime.text = "15:00"
    }

    private fun bindListeners() {
        itemView.setOnClickListener {
            callback.onClickDrug(adapterPosition)
        }
    }


    interface Callback {
        fun onClickDrug(position: Int)
    }

}
