package br.com.sailboat.elseapp.view.ui.insert

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity

class InsertDrugActivity : BaseActivity<InsertDrugFragment>() {

    override fun newFragmentInstance(): InsertDrugFragment {
        return InsertDrugFragment()
    }

    companion object {

        fun start(fragment: Fragment, requestCode: Int) {
            val starter = Intent(fragment.activity, InsertDrugActivity::class.java)
            fragment.startActivityForResult(starter, requestCode)
        }

    }

}
