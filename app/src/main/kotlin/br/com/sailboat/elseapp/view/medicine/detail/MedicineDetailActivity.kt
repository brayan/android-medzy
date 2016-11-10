package br.com.sailboat.elseapp.view.medicine.detail

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.model.MedicineVHModel

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
