package br.com.sailboat.elseapp.view.ui.insert

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity

class InsertDrugActivity : BaseActivity<InsertDrugFragment>() {

    override fun newFragmentInstance(): InsertDrugFragment {
        return InsertDrugFragment()
    }

    companion object {

        fun start(fromFragment: Fragment, requestCode: Int) {
            val starter = Intent(fromFragment.activity, InsertDrugActivity::class.java)
            fromFragment.startActivityForResult(starter, requestCode)
        }

    }

}
