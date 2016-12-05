package br.com.sailboat.medzy.view.adapter.recycler_item

import br.com.sailboat.canoe.recycler.RecyclerItem
import br.com.sailboat.medzy.view.adapter.ViewType

data class SubheaderRecyclerItem(var subheaderText: Int) : RecyclerItem {

    override fun getViewType(): Int {
        return ViewType.SUBHEADER
    }

}
