package br.com.sailboat.elseapp.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import br.com.sailboat.elseapp.R
import br.com.sailboat.elseapp.model.Drug
import br.com.sailboat.elseapp.view.adapter.view_holder.DrugViewHolder


class DrugListAdapter(items: List<Drug>, callback: DrugListAdapter.Callback) : RecyclerView.Adapter<DrugViewHolder>() {

    var drugList: List<Drug>
    var callback: Callback

    init {
        this.drugList = items
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugViewHolder {
        val view = inflateLayout(parent, R.layout.holder_drug)
        return DrugViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: DrugViewHolder, position: Int) {
        val drug = drugList.get(position)
        holder.onBindViewHolder(drug)
    }

    override fun getItemCount(): Int {
        return drugList.size
    }

    private fun inflateLayout(parent: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }


    interface Callback : DrugViewHolder.Callback
}
