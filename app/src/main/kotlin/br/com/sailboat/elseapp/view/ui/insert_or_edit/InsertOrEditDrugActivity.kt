package br.com.sailboat.elseapp.view.ui.insert_or_edit

import android.content.Intent
import android.support.v4.app.Fragment
import br.com.sailboat.elseapp.base.BaseActivity

class InsertOrEditDrugActivity : BaseActivity<InsertOrEditDrugFragment>() {

    override fun newFragmentInstance(): InsertOrEditDrugFragment {
        return InsertOrEditDrugFragment()
    }

    companion object {

        fun start(fragment: Fragment, requestCode: Int) {
            val starter = Intent(fragment.activity, javaClass)
            fragment.startActivityForResult(starter, requestCode)
        }

    }

}
