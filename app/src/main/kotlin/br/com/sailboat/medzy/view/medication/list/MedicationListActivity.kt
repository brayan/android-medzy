package br.com.sailboat.medzy.view.medication.list

import br.com.sailboat.canoe.base.BaseActivitySingleFragment

class MedicationListActivity : BaseActivitySingleFragment<MedicationListFragment>() {

    override fun newFragmentInstance(): MedicationListFragment {
        return MedicationListFragment()
    }

}
