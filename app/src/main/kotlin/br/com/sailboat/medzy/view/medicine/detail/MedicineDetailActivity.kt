package br.com.sailboat.medzy.view.medicine.detail

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.canoe.base.BaseActivity
import br.com.sailboat.medzy.helper.ExtrasHelper

class MedicineDetailActivity : BaseActivity<MedicineDetailFragment>() {

    companion object {

        fun start(fromFragment: Fragment, medId: Long, requestCode: Int) {
            val starter = Intent(fromFragment.activity, MedicineDetailActivity::class.java)
            addExtrasToIntent(medId, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun addExtrasToIntent(medId: Long, starter: Intent) {
            ExtrasHelper.putMedicineId(medId, starter)
        }

    }

    override fun newFragmentInstance(): MedicineDetailFragment {
        return MedicineDetailFragment()
    }

}
