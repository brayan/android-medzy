package br.com.sailboat.elseapp.view.drug.detail

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.model.Drug

class DrugDetailActivity : BaseActivity<DrugDetailFragment>() {

    override fun newFragmentInstance(): DrugDetailFragment {
        return DrugDetailFragment()
    }

    companion object {

        fun start(fromFragment: Fragment, drug: Drug, requestCode: Int) {
            val starter = Intent(fromFragment.activity, DrugDetailActivity::class.java)
            ExtrasHelper.putDrug(drug, starter)
            fromFragment.startActivityForResult(starter, requestCode)
        }

    }

}
