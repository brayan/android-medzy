package br.com.sailboat.medzy.view.adapter.view_holder

import android.support.v4.content.ContextCompat
import android.view.View
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.rules.MedRules
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import kotlinx.android.synthetic.main.holder_medication.view.*
import java.util.*

class MedicationViewHolder(itemView: View, callback: MedicationViewHolder.Callback) : BaseViewHolder(itemView) {

    private val callback = callback
    lateinit var item: MedicationRecyclerItem

    companion object {
        val LAYOUT_ID = R.layout.holder_medication
    }

    override fun <T : Any?> onBindViewHolder(item: T) {
        item as MedicationRecyclerItem

        this.item = item

        itemView.tvHolderMedicationName.text = item.medName
        itemView.tvHolderMedicationAlarmTime.text = formatTime(item.alarmTime)
        initMessage()
        initAlarmTimeColor()
    }

    private fun initMessage() {
        if (MedRules.isOutOfStock(item.totalAmount, item.amount)) {
            itemView.tvHolderMedicationMsg.visibility = View.VISIBLE
            itemView.tvHolderMedicationMsg.setText((R.string.out_of_stock))
            itemView.tvHolderMedicationMsg.setTextColor(getColorFromResource(R.color.red_500))
        } else {
            itemView.tvHolderMedicationMsg.visibility = View.GONE
        }
    }

    private fun initAlarmTimeColor() {
        if (MedRules.isOutOfStock(item.totalAmount, item.amount)) {
            itemView.tvHolderMedicationAlarmTime.setTextColor(getColorFromResource(R.color.red_500))

        } else if (DateHelper.isBeforeNow(item.alarmTime)) {
            itemView.tvHolderMedicationAlarmTime.setTextColor(getColorFromResource(R.color.grey_500))

        } else {
            itemView.tvHolderMedicationAlarmTime.setTextColor(getColorFromResource(R.color.light_blue_500))
        }
    }

    private fun isMedWithoutStock(item: MedicationRecyclerItem): Boolean {
        return item.totalAmount <= 0
    }

    private fun getColorFromResource(colorId: Int): Int {
        return ContextCompat.getColor(itemView.context, colorId)
    }

    override fun bindCallbacks() {
        itemView.setOnClickListener {
            callback.onClickMed(adapterPosition)
        }
    }

    private fun formatTime(time: Calendar): String {
        return DateHelper.formatTimeWithAndroidFormat(itemView.context, time)
    }


    interface Callback {
        fun onClickMed(position: Int)
    }

}
