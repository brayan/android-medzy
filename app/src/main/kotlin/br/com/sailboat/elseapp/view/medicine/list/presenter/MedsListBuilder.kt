package br.com.sailboat.elseapp.view.medicine.list.presenter

import br.com.sailboat.elseapp.common.helper.AlarmHelper
import br.com.sailboat.elseapp.common.helper.CalendarHelper
import br.com.sailboat.elseapp.model.MedicineVHModel
import java.util.*

class MedsListBuilder private constructor(meds: List<MedicineVHModel>) {

    private var meds = meds;


    companion object {

        fun buildFrom(meds: List<MedicineVHModel>) {
            MedsListBuilder(meds).build()

        }
    }

    fun build() {

        val beforeTodayMeds = ArrayList<MedicineVHModel>()
        val todayMeds = ArrayList<MedicineVHModel>()
        val tomorrowMeds = ArrayList<MedicineVHModel>()
        val nextDaysMeds = ArrayList<MedicineVHModel>()


        for (med in meds) {

            if (CalendarHelper.isBeforeToday(med.alarmTime)) {
                beforeTodayMeds.add(med)

            } else if (CalendarHelper.isToday(med.alarmTime)) {
                todayMeds.add(med)

            } else if (CalendarHelper.isTomorrow(med.alarmTime)) {
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

