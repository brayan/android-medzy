package br.com.sailboat.elseapp.view.adapter.view_holder

import android.support.v4.content.ContextCompat
import android.view.View
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.helper.DateHelper
import br.com.sailboat.helper.base.BaseViewHolder
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

        if (DateHelper.isBeforeNow(item.alarmTime)) {
            itemView.tvHolderMedicineAlarmTime.setTextColor(getColorFromResource(R.color.grey_500))
        } else {
            itemView.tvHolderMedicineAlarmTime.setTextColor(getColorFromResource(R.color.light_blue_500))
        }
    }

    private fun getColorFromResource(colorId: Int): Int {
        return ContextCompat.getColor(itemView.context, colorId)
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
