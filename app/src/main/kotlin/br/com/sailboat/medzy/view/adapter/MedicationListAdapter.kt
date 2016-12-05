package br.com.sailboat.medzy.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.canoe.recycler.RecyclerItem
import br.com.sailboat.medzy.view.adapter.view_holder.MedicationViewHolder
import br.com.sailboat.medzy.view.adapter.view_holder.PaddingViewHolder
import br.com.sailboat.medzy.view.adapter.view_holder.SubheaderViewHolder


class MedicationListAdapter(callback: Callback) : RecyclerView.Adapter<BaseViewHolder>() {

    private val callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            ViewType.MEDICATION -> {
                val view = BaseViewHolder.inflateLayout(parent, MedicationViewHolder.LAYOUT_ID)
                return MedicationViewHolder(view, callback)
            }
            ViewType.PADDING -> {
                val view = BaseViewHolder.inflateLayout(parent, PaddingViewHolder.LAYOUT_ID)
                return PaddingViewHolder(view)
            }
            else -> {
                val view = BaseViewHolder.inflateLayout(parent, SubheaderViewHolder.LAYOUT_ID)
                return SubheaderViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = callback.meds.get(position)
        holder.onBindViewHolder(item)
    }

    override fun getItemCount(): Int {
        return callback.meds.size
    }

    override fun getItemViewType(position: Int): Int {
        return callback.meds[position].viewType
    }


    interface Callback : MedicationViewHolder.Callback {
        val meds: List<RecyclerItem>
    }
}