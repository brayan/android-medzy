package br.com.sailboat.medzy.view.adapter.view_holder;

import android.view.View
import android.view.ViewGroup
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.view.adapter.recycler_item.SubheaderRecyclerItem
import kotlinx.android.synthetic.main.holder_subheader.view.*

class SubheaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup) : SubheaderViewHolder {
            val view = inflateLayout(parent, R.layout.holder_subheader)
            return SubheaderViewHolder(view)
        }
    }

    fun onBindViewHolder(item: SubheaderRecyclerItem) {
        itemView.tvHolderSubheader.setText(item.subheaderText)
    }

}