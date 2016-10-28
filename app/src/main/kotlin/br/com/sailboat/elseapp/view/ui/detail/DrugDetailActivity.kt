package br.com.sailboat.elseapp.view.ui.detail

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity
import br.com.sailboat.elseapp.common.helper.ExtrasHelper
import br.com.sailboat.elseapp.model.Drug

class DrugDetailActivity : BaseActivity<DrugDetailFragment>() {

    override fun newFragmentInstance(): DrugDetailFragment {
        return DrugDetailFragment()
    }

//    companion object {
//
//        fun start(fragment: Fragment, drug: Drug) {
//            val starter = Intent(fragment.activity, DrugDetailActivity.javaClass)
//            ExtrasHelper.putDrug(drug, starter)
//            fragment.startActivity(starter)
//        }
//
//    }

}
