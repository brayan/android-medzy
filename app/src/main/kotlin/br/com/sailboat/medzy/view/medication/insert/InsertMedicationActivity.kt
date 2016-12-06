package br.com.sailboat.medzy.view.medication.insert

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.canoe.base.BaseActivitySingleFragment
import br.com.sailboat.medzy.helper.ExtrasHelper

class InsertMedicationActivity : BaseActivitySingleFragment<InsertMedicationFragment>() {

    companion object {

        fun start(fromFragment: Fragment, requestCode: Int) {
            start(fromFragment, null, requestCode)
        }

        fun start(fromFragment: Fragment, medId: Long?, requestCode: Int) {
            val starter = Intent(fromFragment.activity, InsertMedicationActivity::class.java)
            addMedIdToIntent(medId, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun addMedIdToIntent(medId: Long?, starter: Intent) {
            medId?.let {
                ExtrasHelper.putMedicationId(medId, starter)
            }
        }

    }

    override fun newFragmentInstance(): InsertMedicationFragment {
        return InsertMedicationFragment()
    }

}
