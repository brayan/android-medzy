package br.com.sailboat.elseapp.view.medicine.list

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.common.helper.DialogHelper
import br.com.sailboat.elseapp.model.MedicineVHModel
import br.com.sailboat.elseapp.view.adapter.MedicineListAdapter
import br.com.sailboat.elseapp.view.medicine.detail.MedicineDetailActivity
import br.com.sailboat.elseapp.view.medicine.insert.InsertMedicineActivity
import br.com.sailboat.elseapp.view.medicine.list.presenter.MedicineListPresenter
import kotlinx.android.synthetic.main.empty_meds.*
import kotlinx.android.synthetic.main.fab.*
import kotlinx.android.synthetic.main.recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*

class MedicineListFragment : BaseFragment<MedicineListPresenter>(), MedicineListPresenter.View {

    private val REQUEST_NEW_MEDICINE = 0
    private val REQUEST_DETAILS = 1

    override val LAYOUT_ID = R.layout.frag_medicine_list

    override fun newPresenterInstance(): MedicineListPresenter {
        return MedicineListPresenter(this)
    }

    override fun initViews() {
        initRecyclerView()
        initEmptyMedsView()
        initToolbar()
        initFab()
    }

    override fun updateMedicines() {
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun showDialog(message: String) {
        DialogHelper.showMessage(fragmentManager, message)
    }

    override fun startInsertMedicineActivity() {
        InsertMedicineActivity.start(this, REQUEST_NEW_MEDICINE)
    }

    override fun startMedicineDetailActivity(medicine: MedicineVHModel) {
        MedicineDetailActivity.start(this, medicine, REQUEST_DETAILS)
    }

    override fun updateWorkoutRemoved(position: Int) {
        recyclerView.adapter.notifyItemRemoved(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult()
    }

    override fun hideEmptyList() {
        emptyMeds.visibility = View.GONE
    }

    override fun showEmptyList() {
        emptyMeds.visibility = View.VISIBLE
    }

    override fun hideMedicines() {
        recyclerView.visibility = View.GONE
    }

    override fun showMedicines() {
        recyclerView.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MedicineListAdapter(presenter)
    }

    private fun initEmptyMedsView() {
        emptyMeds.visibility = View.GONE
    }

    private fun initToolbar() {
        toolbar.setTitle(R.string.app_name)
    }

    private fun initFab() {
        fab.setOnClickListener {
            presenter.onClickNewMedicine()
        }
    }

}
