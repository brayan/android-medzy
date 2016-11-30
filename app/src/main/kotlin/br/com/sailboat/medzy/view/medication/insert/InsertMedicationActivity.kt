package br.com.sailboat.medzy.view.medication.insert

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.canoe.base.BaseActivity
import br.com.sailboat.medzy.helper.ExtrasHelper

class InsertMedicationActivity : BaseActivity<InsertMedicationFragment>() {

    companion object {

        fun start(fromFragment: Fragment, requestCode: Int) {
            start(fromFragment, null, requestCode)
        }

        fun start(fromFragment: Fragment, medicineId: Long?, requestCode: Int) {
            val starter = Intent(fromFragment.activity, InsertMedicationActivity::class.java)
            addMedicineIdToIntent(medicineId, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun addMedicineIdToIntent(medicineId: Long?, starter: Intent) {
            medicineId?.let {
                ExtrasHelper.putMedicationId(medicineId, starter)
            }
        }

    }

    override fun newFragmentInstance(): InsertMedicationFragment {
        return InsertMedicationFragment()
    }

}
