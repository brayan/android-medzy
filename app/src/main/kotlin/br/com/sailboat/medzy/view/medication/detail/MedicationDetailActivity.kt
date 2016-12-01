package br.com.sailboat.medzy.view.medication.detail

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.canoe.base.BaseActivitySingleFragment
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.medzy.view.medication.detail.MedicationDetailFragment

class MedicationDetailActivity : BaseActivitySingleFragment<MedicationDetailFragment>() {

    companion object {

        fun start(fromFragment: Fragment, medId: Long, requestCode: Int) {
            val starter = Intent(fromFragment.activity, MedicationDetailActivity::class.java)
            addExtrasToIntent(medId, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun addExtrasToIntent(medId: Long, starter: Intent) {
            ExtrasHelper.putMedicationId(medId, starter)
        }

    }

    override fun newFragmentInstance(): MedicationDetailFragment {
        val medId = ExtrasHelper.getMedicationId(intent)!!
        return MedicationDetailFragment.newInstance(medId)
    }

}
