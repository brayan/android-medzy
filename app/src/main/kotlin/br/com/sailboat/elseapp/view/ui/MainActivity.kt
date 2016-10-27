package br.com.sailboat.elseapp.view.ui

import br.com.sailboat.elseapp.base.BaseActivity

class MainActivity : BaseActivity<DrugListFragment>() {

    override fun newFragmentInstance(): DrugListFragment {
        return DrugListFragment()
    }

}
