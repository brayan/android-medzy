package br.com.sailboat.medzy.view.medicine.detail

import android.app.Activity
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import br.com.sailboat.canoe.base.BaseFragment
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.view.medicine.detail.presenter.MedicineDetailPresenter
import br.com.sailboat.medzy.view.medicine.insert.InsertMedicineActivity
import kotlinx.android.synthetic.main.frag_medicine_detail.*

class MedicineDetailFragment : BaseFragment<MedicineDetailPresenter>(), MedicineDetailPresenter.View {

    private val REQUEST_EDIT_MEDICINE = 0

    override fun getLayoutId(): Int {
        return R.layout.frag_medicine_detail
    }

    override fun newPresenterInstance(): MedicineDetailPresenter {
        return MedicineDetailPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_medicine_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_item_delete -> {
                presenter.onClickMenuDelete()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    override fun onActivityResultOk(requestCode: Int, data: Intent?) {

        when (requestCode) {
            REQUEST_EDIT_MEDICINE -> {
                presenter.onActivityResultOkEditMedicine()
                return
            }
        }

    }

    override fun setMedicineName(name: String) {
        tvMedicineDetailName.text = name
    }

    override fun closeActivityResultOk() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun startInsertMedicineActivity(medicineId: Long) {
        InsertMedicineActivity.start(this, medicineId, REQUEST_EDIT_MEDICINE)
    }

    override fun initViews(view: View) {
        initToolbar(view)
        initFab(view)
    }

    private fun initFab(view: View) {
        val fab = view.findViewById(R.id.fab) as FloatingActionButton
        fab.setImageResource(R.drawable.ic_edit_white_24dp)
        fab.setOnClickListener {
            presenter.onClickEdit()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle("Medicine Detail")
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }


}
