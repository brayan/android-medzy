package br.com.sailboat.medzy.view.adapter.view_holder

import android.view.View
import android.view.ViewGroup
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.medzy.R
import br.com.sailboat.medzy.view.adapter.recycler_item.PaddingRecyclerItem

class PaddingViewHolder(itemView: View) : BaseViewHolder(itemView) {

    companion object {

        fun newInstance(parent: ViewGroup) : PaddingViewHolder {
            val view = inflateLayout(parent, R.layout.vh_padding)
            return PaddingViewHolder(view)
        }
    }

    fun onBindViewHolder(item: PaddingRecyclerItem) {
        itemView.getLayoutParams().height = item.height
    }


}