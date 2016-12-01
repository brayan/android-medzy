package br.com.sailboat.medzy.view.medication.list.presenter

import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import java.util.*

class MedsListBuilder private constructor(meds: List<MedicationRecyclerItem>) {

    private var meds = meds;


    companion object {

        fun buildFrom(meds: List<MedicationRecyclerItem>) {
            MedsListBuilder(meds).build()

        }
    }

    fun build() {

        val beforeTodayMeds = ArrayList<MedicationRecyclerItem>()
        val todayMeds = ArrayList<MedicationRecyclerItem>()
        val tomorrowMeds = ArrayList<MedicationRecyclerItem>()
        val nextDaysMeds = ArrayList<MedicationRecyclerItem>()


        for (med in meds) {

            if (DateHelper.isBeforeToday(med.alarmTime)) {
                beforeTodayMeds.add(med)

            } else if (DateHelper.isToday(med.alarmTime)) {
                todayMeds.add(med)

            } else if (DateHelper.isTomorrow(med.alarmTime)) {
                tomorrowMeds.add(med)

            } else {
                nextDaysMeds.add(med)
            }

        }


        if (beforeTodayMeds.isNotEmpty()) {
            // add a new category
            // add the list to the collection
        }


    }

}

