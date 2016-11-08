package br.com.sailboat.elseapp.view.medicine.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.base.BaseFragment
import br.com.sailboat.elseapp.model.Medicine
import br.com.sailboat.elseapp.view.medicine.detail.presenter.MedicineDetailPresenter
import br.com.sailboat.elseapp.view.medicine.insert.InsertMedicineActivity
import kotlinx.android.synthetic.main.fab.*
import kotlinx.android.synthetic.main.frag_medicine_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class MedicineDetailFragment : BaseFragment<MedicineDetailPresenter>(), MedicineDetailPresenter.View {

    private val REQUEST_EDIT_DRUG = 0

    override val layoutId: Int get() = R.layout.frag_medicine_detail
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): MedicineDetailPresenter {
        return MedicineDetailPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_drug_detail, menu)
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
            REQUEST_EDIT_DRUG -> {
                presenter.onActivityResultOkEditDrug(data)
                return
            }
        }

    }

    override fun setDrugName(name: String) {
        tvMedicineDetailName.text = name
    }

    override fun closeActivityResultOk() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun startInsertDrugActivity(medicineId: Long) {
        InsertMedicineActivity.start(this, medicineId, REQUEST_EDIT_DRUG)
    }

    override fun initViews() {
        initToolbar()
        fab.setImageResource(R.drawable.ic_edit_white_24dp)
        fab.setOnClickListener {
            presenter.onClickEdit()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initToolbar() {
        toolbar.setTitle("Drug Detail")
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }


}
