package br.com.sailboat.medzy.view.medicine.insert

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.medzy.helper.ExtrasHelper
import br.com.sailboat.canoe.base.BaseActivity

class InsertMedicineActivity : BaseActivity<InsertMedicineFragment>() {

    companion object {

        fun start(fromFragment: Fragment, requestCode: Int) {
            start(fromFragment, null, requestCode)
        }

        fun start(fromFragment: Fragment, medicineId: Long?, requestCode: Int) {
            val starter = Intent(fromFragment.activity, InsertMedicineActivity::class.java)
            addMedicineIdToIntent(medicineId, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun addMedicineIdToIntent(medicineId: Long?, starter: Intent) {
            medicineId?.let {
                ExtrasHelper.putMedicineId(medicineId, starter)
            }
        }

    }

    override fun newFragmentInstance(): InsertMedicineFragment {
        return InsertMedicineFragment()
    }

}
