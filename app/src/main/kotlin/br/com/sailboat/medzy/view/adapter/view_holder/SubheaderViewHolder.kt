package br.com.sailboat.medzy.view.adapter.view_holder;

import android.view.View
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.view.adapter.recycler_item.SubheaderRecyclerItem
import kotlinx.android.synthetic.main.holder_subheader.view.*

class SubheaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

    companion object {
        val LAYOUT_ID = R.layout.holder_subheader
    }

    override fun <T : Any?> onBindViewHolder(item: T) {
        item as SubheaderRecyclerItem
        itemView.tvHolderSubheader.setText(item.subheaderText)
    }

}