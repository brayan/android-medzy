package br.com.sailboat.medzy.view.adapter.view_holder

import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.use_case.MedicationUseCase
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import kotlinx.android.synthetic.main.holder_medication.view.*
import java.util.*

class MedicationViewHolder(itemView: View, callback: MedicationViewHolder.Callback) : BaseViewHolder(itemView) {

    private val callback = callback
    lateinit var item: MedicationRecyclerItem

    companion object {

        fun newInstance(parent: ViewGroup, callback: MedicationViewHolder.Callback): MedicationViewHolder {
            val view = inflateLayout(parent, R.layout.holder_medication)
            return MedicationViewHolder(view, callback)
        }
    }

    override fun <T : Any?> onBindViewHolder(item: T) {
        item as MedicationRecyclerItem

        this.item = item

        itemView.tvHolderMedicationName.text = item.medName
        itemView.tvHolderMedicationAlarmTime.text = formatTime(item.alarmTime)

        initDate(item)
        initMessage()
        initDateTimeColor()
        initDateTimeVisibility()
    }

    private fun initDate(item: MedicationRecyclerItem) {
        itemView.tvHolderMedicationAlarmDate.text = MedicationUseCase.getDateMedicationHolder(itemView.context, item.alarmTime)
    }

    private fun initDateTimeVisibility() {
        if (DateHelper.isBeforeToday(item.alarmTime) || DateHelper.isAfterTomorrow(item.alarmTime)) {
            itemView.tvHolderMedicationAlarmTime.visibility = View.GONE
            itemView.tvHolderMedicationAlarmDate.visibility = View.VISIBLE

        } else {
            itemView.tvHolderMedicationAlarmTime.visibility = View.VISIBLE
            itemView.tvHolderMedicationAlarmDate.visibility = View.GONE
        }
    }

    private fun initMessage() {
        if (MedicationUseCase.isOutOfStock(item.totalAmount, item.amount)) {
            itemView.tvHolderMedicationMsg.visibility = View.VISIBLE
            itemView.tvHolderMedicationMsg.setText((R.string.out_of_stock))
            itemView.tvHolderMedicationMsg.setTextColor(ContextCompat.getColor(itemView.context, R.color.red_500))
        } else {
            itemView.tvHolderMedicationMsg.visibility = View.GONE
        }
    }

    private fun initDateTimeColor() {
        val color = MedicationUseCase.getDateTimeMedHolderColor(itemView.context, item.alarmTime, item.totalAmount, item.amount)
        itemView.tvHolderMedicationAlarmTime.setTextColor(color)
        itemView.tvHolderMedicationAlarmDate.setTextColor(color)
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
