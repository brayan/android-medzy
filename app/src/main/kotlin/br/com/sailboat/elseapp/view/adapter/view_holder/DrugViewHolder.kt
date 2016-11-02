package br.com.sailboat.elseapp.view.adapter.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.model.Drug
import kotlinx.android.synthetic.main.holder_drug.view.*

class DrugViewHolder(itemView: View, callback: DrugViewHolder.Callback) : RecyclerView.ViewHolder(itemView) {

    private val callback: Callback?

    init {
        this.callback = callback
        bindCallback()
    }

    fun onBindViewHolder(drug: Drug) {
        itemView.tvHolderDrugName.text = drug.name
        itemView.tvHolderDrugAlarmTime.text = formatTime(drug)
    }

    private fun bindCallback() {

        callback?.let {
            itemView.setOnClickListener {
                callback.onClickDrug(adapterPosition)
            }
        }

    }

    private fun formatTime(drug: Drug) : String {
        return AlarmHelper.formatTimeWithAndroidFormat(drug.alarm.time, itemView.context)
    }


    interface Callback {
        fun onClickDrug(position: Int)
    }

}
