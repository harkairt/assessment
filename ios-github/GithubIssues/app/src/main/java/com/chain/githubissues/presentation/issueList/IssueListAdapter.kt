package com.chain.githubissues.presentation.issueList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chain.githubissues.databinding.IssueListItemBinding
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.presentation.common.BaseAdapter

class IssueListAdapter(init: IssueListAdapter.() -> Unit) : BaseAdapter<IssueListItemViewHolder, Issue>() {
    init {
        init()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueListItemViewHolder {
        return IssueListItemViewHolder(
                IssueListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: IssueListItemViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.bind(getDataSource()[position])
    }
}

class IssueListItemViewHolder(private val itemBinding: IssueListItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(issue: Issue) {
        itemBinding.issue = issue
    }
}