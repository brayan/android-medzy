package br.com.sailboat.medzy.view.medicine.detail

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.canoe.base.BaseActivity
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.view.adapter.view_holder.MedicineVHModel

class MedicineDetailActivity : BaseActivity<MedicineDetailFragment>() {

    companion object {

        fun start(fromFragment: Fragment, medicine: MedicineVHModel, requestCode: Int) {
            val starter = Intent(fromFragment.activity, MedicineDetailActivity::class.java)
            addExtrasToIntent(medicine, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun addExtrasToIntent(medicine: MedicineVHModel, starter: Intent) {
            ExtrasHelper.putMedicineName(medicine.medicineName, starter)
            ExtrasHelper.putMedicineId(medicine.medicineId, starter)
        }

    }

    override fun newFragmentInstance(): MedicineDetailFragment {
        return MedicineDetailFragment()
    }

}
