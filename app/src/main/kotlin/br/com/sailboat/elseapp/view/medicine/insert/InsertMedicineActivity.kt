package br.com.sailboat.elseapp.view.medicine.insert

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.model.Medicine

class InsertMedicineActivity : BaseActivity<InsertMedicineFragment>() {

    override fun newFragmentInstance(): InsertMedicineFragment {
        return InsertMedicineFragment()
    }

    companion object {

        fun start(fromFragment: Fragment, requestCode: Int) {
            start(fromFragment, null, requestCode)
        }

        fun start(fromFragment: Fragment, medicine: Medicine?, requestCode: Int) {
            val starter = Intent(fromFragment.activity, InsertMedicineActivity::class.java)
            checkAndAddDrugToIntent(medicine, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun checkAndAddDrugToIntent(medicine: Medicine?, starter: Intent) {
            medicine?.let {
                ExtrasHelper.putMedicine(medicine, starter)
            }
        }

    }

}
