package br.com.sailboat.medzy.view.medication.list.presenter

import br.com.sailboat.canoe.helper.DateHelper
import br.com.sailboat.canoe.recycler.RecyclerItem
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import br.com.sailboat.medzy.view.adapter.recycler_item.PaddingRecyclerItem
import br.com.sailboat.medzy.view.adapter.recycler_item.SubheaderRecyclerItem
import java.util.*

class MedsListBuilder private constructor(meds: List<MedicationRecyclerItem>) {

    private val meds = meds

    private val previousDaysMeds = ArrayList<RecyclerItem>()
    private val todayMeds = ArrayList<RecyclerItem>()
    private val tomorrowMeds = ArrayList<RecyclerItem>()
    private val nextDaysMeds = ArrayList<RecyclerItem>()

    companion object {

        fun buildFrom(meds: List<MedicationRecyclerItem>): List<RecyclerItem> {
            return MedsListBuilder(meds).build()
        }

    }

    fun build(): List<RecyclerItem> {
        bindMedsToCategories()
        bindSubheader()
        val medsList = bindAllListsTogether()

        bindPadding(medsList)

        return medsList
    }

    private fun bindMedsToCategories() {
        for (med in meds) {

            val calendar = DateHelper.parseStringWithDatabaseFormatToCalendar(med.alarmTime)

            if (DateHelper.isBeforeToday(calendar)) {
                previousDaysMeds.add(med)

            } else if (DateHelper.isToday(calendar)) {
                todayMeds.add(med)

            } else if (DateHelper.isTomorrow(calendar)) {
                tomorrowMeds.add(med)

            } else {
                nextDaysMeds.add(med)
            }

        }
    }

    private fun bindSubheader() {
        if (previousDaysMeds.isNotEmpty()) {
            previousDaysMeds.add(0, SubheaderRecyclerItem(R.string.previous_days))
        }

        if (todayMeds.isNotEmpty()) {
            todayMeds.add(0, SubheaderRecyclerItem(R.string.today))
        }

        if (tomorrowMeds.isNotEmpty()) {
            tomorrowMeds.add(0, SubheaderRecyclerItem(R.string.tomorrow))
        }

        if (nextDaysMeds.isNotEmpty()) {
            nextDaysMeds.add(0, SubheaderRecyclerItem(R.string.next_days))
        }
    }

    private fun bindPadding(medsList: ArrayList<RecyclerItem>) {
        if (medsList.isNotEmpty()) {
            medsList.add(PaddingRecyclerItem(180))
        }
    }

    private fun bindAllListsTogether(): ArrayList<RecyclerItem> {
        val medsList = ArrayList<RecyclerItem>()
        medsList.addAll(previousDaysMeds)
        medsList.addAll(todayMeds)
        medsList.addAll(tomorrowMeds)
        medsList.addAll(nextDaysMeds)

        return medsList
    }

}

