package br.com.sailboat.medzy.view.medicine.list

import br.com.sailboat.canoe.base.BaseActivity

class MedicationListActivity : BaseActivity<MedicationListFragment>() {

    override fun newFragmentInstance(): MedicationListFragment {
        return MedicationListFragment()
    }

}
