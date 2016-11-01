package br.com.sailboat.elseapp.view.drug.detail

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
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.drug.detail.presenter.DrugDetailPresenter
import br.com.sailboat.elseapp.view.drug.insert.InsertDrugActivity
import kotlinx.android.synthetic.main.frag_drug_detail.*
import kotlinx.android.synthetic.main.toolbar.*

class DrugDetailFragment : BaseFragment<DrugDetailPresenter>(), DrugDetailPresenter.View {

    private val REQUEST_EDIT_DRUG = 0

    override val layoutId: Int get() = R.layout.frag_drug_detail
    override val activityContext: Context get() = activity

    override fun newPresenterInstance(): DrugDetailPresenter {
        return DrugDetailPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_drug_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_item_edit -> {
                presenter.onClickMenuEdit()
                return true
            }
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
        tvDrugDetailName.text = name
    }

    override fun closeActivityResultOk() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun startInsertDrugActivity(drug: Drug) {
        InsertDrugActivity.start(this, drug, REQUEST_EDIT_DRUG)
    }

    override fun initViews() {
        initToolbar()
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    private fun initToolbar() {
        toolbar.setTitle("Drug Detail")
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }


}
