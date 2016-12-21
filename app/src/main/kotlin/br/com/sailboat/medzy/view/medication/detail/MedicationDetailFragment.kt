package br.com.sailboat.medzy.view.medication.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import br.com.sailboat.medzy.view.medication.detail.presenter.MedicationDetailPresenter
import br.com.sailboat.medzy.view.medication.insert.InsertMedicationActivity
import kotlinx.android.synthetic.main.frag_medication_detail.*
import kotlinx.android.synthetic.main.med_content.*


class MedicationDetailFragment : BaseFragment<MedicationDetailPresenter>(), MedicationDetailPresenter.View {

    private val REQUEST_EDIT_MEDICINE = 0
    private lateinit var toolbar: Toolbar

    companion object {

        fun newInstance(medId: Long): MedicationDetailFragment {
            val fragment = MedicationDetailFragment()

            val args = Bundle()
            args.putLong("MEDICATION_ID", medId)
            fragment.setArguments(args)

            return fragment
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.frag_medication_detail
    }

    override fun newPresenterInstance(): MedicationDetailPresenter {
        return MedicationDetailPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_medication_detail, menu)
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

    override fun postActivityResult(requestCode: Int, data: Intent?) {
        presenter.postActivityResult()
    }

    override fun setMedicationName(name: String) {
        tvFragMedicationDetailName.text = name
    }

    override fun setTotalAmount(total: String) {
        tvMedContentTotalAmount.text = total
    }

    override fun setAlarmTime(time: String) {
        tvMedContentAlarmTime.setText(time)
    }

    override fun setAlarmAmount(amount: String) {
        tvMedContentAlarmAmount.text = amount
    }

    override fun closeActivityResultOk() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun startInsertMedicationActivity(medicationId: Long) {
        InsertMedicationActivity.start(this, medicationId, REQUEST_EDIT_MEDICINE)
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
        toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setTitle(getString(R.string.med_detail))
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }


}
