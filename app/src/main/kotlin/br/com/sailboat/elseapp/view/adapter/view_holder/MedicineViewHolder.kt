package br.com.sailboat.elseapp.view.adapter.view_holder

import android.view.View
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseViewHolder
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.helper.DateHelper
import kotlinx.android.synthetic.main.holder_medicine.view.*
import java.util.*

class MedicineViewHolder(itemView: View, callback: MedicineViewHolder.Callback) : BaseViewHolder<MedicineVHModel>(itemView) {

    private val callback = callback

    companion object {
        val LAYOUT_ID = R.layout.holder_medicine
    }

    override fun onBindViewHolder(item: MedicineVHModel) {
        itemView.tvHolderMedicineName.text = item.medicineName
        itemView.tvHolderMedicineAlarmTime.text = formatTime(item.alarmTime.time)
    }

    override fun bindCallbacks() {
        itemView.setOnClickListener {
            callback.onClickMedicine(adapterPosition)
        }
    }

    private fun formatTime(time: Date): String {
        return DateHelper.formatTimeWithAndroidFormat(time, itemView.context)
    }


    interface Callback {
        fun onClickMedicine(position: Int)
    }

}
