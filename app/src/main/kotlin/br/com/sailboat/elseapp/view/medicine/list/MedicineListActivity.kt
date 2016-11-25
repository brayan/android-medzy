package br.com.sailboat.elseapp.view.medicine.list

import br.com.sailboat.canoe.base.BaseActivity

class MedicineListActivity : BaseActivity<MedicineListFragment>() {

    override fun newFragmentInstance(): MedicineListFragment {
        return MedicineListFragment()
    }

}
