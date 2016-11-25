package br.com.sailboat.medzy.view.medicine.list

import br.com.sailboat.canoe.base.BaseActivity

class MedicineListActivity : BaseActivity<MedicineListFragment>() {

    override fun newFragmentInstance(): MedicineListFragment {
        return MedicineListFragment()
    }

}
