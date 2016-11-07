package br.com.sailboat.elseapp.view.adapter.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.model.MedicineVHModel
import kotlinx.android.synthetic.main.holder_medicine.view.*
import java.util.*

class MedicineViewHolder(itemView: View, callback: MedicineViewHolder.Callback) : RecyclerView.ViewHolder(itemView) {

    private val callback: Callback?

    init {
        this.callback = callback
        bindCallback()
    }

    fun onBindViewHolder(medicine: MedicineVHModel) {
        itemView.tvHolderMedicineName.text = medicine.name
        itemView.tvHolderMedicineAlarmTime.text = formatTime(medicine.alarm.time)
    }

    private fun bindCallback() {

        callback?.let {
            itemView.setOnClickListener {
                callback.onClickDrug(adapterPosition)
            }
        }

    }

    private fun formatTime(time: Date) : String {
        return AlarmHelper.formatTimeWithAndroidFormat(time, itemView.context)
    }


    interface Callback {
        fun onClickDrug(position: Int)
    }

}
