package br.com.sailboat.medzy.view.adapter.view_holder

import android.support.v4.content.ContextCompat
import android.view.View

import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.R
import kotlinx.android.synthetic.main.holder_medicine.view.*
import java.util.*

class MedicineViewHolder(itemView: View, callback: MedicineViewHolder.Callback) : BaseViewHolder<MedicineVHModel>(itemView) {

    private val callback = callback

    companion object {
        val LAYOUT_ID = R.layout.holder_medicine
    }

    override fun onBindViewHolder(item: MedicineVHModel) {
        itemView.tvHolderMedicineName.text = item.medicineName
        itemView.tvHolderMedicineAlarmTime.text = formatTime(item.alarmTime)

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

    private fun formatTime(time: Calendar): String {
        return DateHelper.formatTimeWithAndroidFormat(itemView.context, time)
    }


    interface Callback {
        fun onClickMedicine(position: Int)
    }

}
