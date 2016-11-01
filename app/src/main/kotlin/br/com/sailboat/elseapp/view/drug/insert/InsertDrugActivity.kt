package br.com.sailboat.elseapp.view.drug.insert

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.model.Drug

class InsertDrugActivity : BaseActivity<InsertDrugFragment>() {

    override fun newFragmentInstance(): InsertDrugFragment {
        return InsertDrugFragment()
    }

    companion object {

        fun start(fromFragment: Fragment, requestCode: Int) {
            start(fromFragment, null, requestCode)
        }

        fun start(fromFragment: Fragment, drug: Drug?, requestCode: Int) {
            val starter = Intent(fromFragment.activity, InsertDrugActivity::class.java)
            checkAndAddDrugToIntent(drug, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

        private fun checkAndAddDrugToIntent(drug: Drug?, starter: Intent) {
            drug?.let {
                ExtrasHelper.putDrug(drug, starter)
            }
        }

    }

}
