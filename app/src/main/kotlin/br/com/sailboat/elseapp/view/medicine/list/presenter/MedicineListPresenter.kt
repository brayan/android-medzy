package br.com.sailboat.elseapp.view.medicine.list.presenter

import android.content.Context
import br.com.sailboat.elseapp.base.BaseAsyncTask
import br.com.sailboat.elseapp.base.BasePresenter
import br.com.sailboat.elseapp.common.helper.LogHelper
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.view.adapter.MedicineListAdapter
import br.com.sailboat.elseapp.view.async_task.AsyncLoadMedicinesViewHolder
import br.com.sailboat.elseapp.view.medicine.list.view_model.MedicineListViewModel


class MedicineListPresenter(view: MedicineListPresenter.View) : BasePresenter(), MedicineListAdapter.Callback {

    private val view = view
    private val viewModel = MedicineListViewModel()

    override val meds: MutableList<MedicineVHModel> get() = viewModel.medicines

    override fun onResumeFirstSession() {
        loadMedicines()
    }

    override fun onResumeAfterRestart() {
        updateContentViews()
    }

    override fun onClickMedicine(position: Int) {
        val medicine = meds[position]
        view.startMedicineDetailActivity(medicine)
    }

    fun onClickNewMedicine() {
        view.startInsertMedicineActivity()
    }

    fun onActivityResult() {
        loadMedicines()
    }

    private fun loadMedicines() {

        AsyncLoadMedicinesViewHolder.load(view.getContext(), object : BaseAsyncTask.Callback<MutableList<MedicineVHModel>> {

            override fun onSuccess(result: MutableList<MedicineVHModel>) {
                meds.clear()
                meds.addAll(result!!)

                // TODO: GENERATE MEDS LIST

                updateContentViews()
            }

            override fun onFail(e: Exception) {
                LogHelper.printExceptionLog(e)
                view.showDialog("An error occurred while loading the meds")
            }

        })

    }

    private fun updateContentViews() {
        updateMedicinesViews()
        updateVisibilityOfMedicines()
    }

    private fun updateVisibilityOfMedicines() {
        if (meds.isEmpty()) {
            view.showEmptyList()
            view.hideMedicines()
        } else {
            view.showMedicines()
            view.hideEmptyList()
        }
    }

    private fun updateMedicinesViews() {
        view.updateMedicines()
    }


    interface View {
        fun getContext(): Context
        fun updateMedicines()
        fun showDialog(message: String)
        fun startInsertMedicineActivity()
        fun startMedicineDetailActivity(medicine: MedicineVHModel)
        fun updateWorkoutRemoved(position: Int)
        fun showMedicines()
        fun hideEmptyList()
        fun showEmptyList()
        fun hideMedicines()
    }

}
