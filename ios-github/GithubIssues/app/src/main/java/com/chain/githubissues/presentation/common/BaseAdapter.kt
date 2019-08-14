package com.chain.githubissues.presentation.common

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH : RecyclerView.ViewHolder, T> : ClickableAdapter<VH, T>(),
    BindableAdapter<List<T>> {

    private var data: List<T> = listOf()

    final override fun getDataSource(): List<T> = data

    override fun bindData(data: List<T>) {
        this.data = data
    }
}