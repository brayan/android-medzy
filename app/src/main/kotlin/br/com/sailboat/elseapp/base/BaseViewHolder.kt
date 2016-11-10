package br.com.sailboat.elseapp.base

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        bindCallbacks()
    }

    abstract fun onBindViewHolder(item: T)

    protected open fun bindCallbacks() { }

}