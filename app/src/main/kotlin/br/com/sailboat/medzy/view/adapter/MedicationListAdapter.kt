package br.com.sailboat.medzy.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import br.com.sailboat.canoe.base.BaseViewHolder
import br.com.sailboat.canoe.recycler.RecyclerItem
import br.com.sailboat.medzy.view.adapter.recycler_item.MedicationRecyclerItem
import br.com.sailboat.medzy.view.adapter.recycler_item.PaddingRecyclerItem
import br.com.sailboat.medzy.view.adapter.recycler_item.SubheaderRecyclerItem
import br.com.sailboat.medzy.view.adapter.view_holder.MedicationViewHolder
import br.com.sailboat.medzy.view.adapter.view_holder.PaddingViewHolder
import br.com.sailboat.medzy.view.adapter.view_holder.SubheaderViewHolder


class MedicationListAdapter(callback: Callback) : RecyclerView.Adapter<BaseViewHolder>() {

    private val callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            ViewType.MEDICATION -> {
                return MedicationViewHolder.newInstance(parent, callback)
            }
            ViewType.PADDING -> {
                return PaddingViewHolder.newInstance(parent)
            }
            ViewType.SUBHEADER -> {
                return SubheaderViewHolder.newInstance(parent)
            }
            else -> {
                throw RuntimeException("ViewHolder not found")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = callback.meds.get(position)

        when (item.viewType) {
            ViewType.MEDICATION -> {
                holder as MedicationViewHolder
                holder.onBindViewHolder(item as MedicationRecyclerItem)
            }
            ViewType.PADDING -> {
                holder as PaddingViewHolder
                holder.onBindViewHolder(item as PaddingRecyclerItem)
            }
            ViewType.SUBHEADER -> {
                holder as SubheaderViewHolder
                holder.onBindViewHolder(item as SubheaderRecyclerItem)
            }
            else -> {
                throw RuntimeException("ViewHolder not found")
            }
        }


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
