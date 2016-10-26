package br.com.sailboat.elseapp

import br.com.sailboat.elseapp.base.BaseActivity
import br.com.sailboat.elseapp.view.DrugListFragment

class MainActivity : BaseActivity<DrugListFragment>() {

    override fun newFragmentInstance(): DrugListFragment {
        return DrugListFragment()
    }

}
