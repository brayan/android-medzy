package br.com.sailboat.elseapp.view.drug.list

import br.com.sailboat.elseapp.base.BaseActivity

class DrugListActivity : BaseActivity<DrugListFragment>() {

    override fun newFragmentInstance(): DrugListFragment {
        return DrugListFragment()
    }

}
