package br.com.sailboat.medzy.view.adapter.view_holder;

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.view.adapter.recycler_item.SubheaderRecyclerItem

class SubheaderViewHolder(itemView: View) : BaseViewHolder(itemView) {

    private var tvSubheader = itemView.findViewById(R.id.tvHolderSubheader) as TextView

    companion object {

        fun newInstance(parent: ViewGroup) : SubheaderViewHolder {
            val view = inflateLayout(parent, R.layout.holder_subheader)
            return SubheaderViewHolder(view)
        }
    }

    fun onBindViewHolder(item: SubheaderRecyclerItem) {
        tvSubheader.setText(item.subheaderText)
    }

}