package com.chain.githubissues.presentation.issueList

import androidx.recyclerview.widget.DiffUtil
import com.chain.githubissues.domain.entity.Issue

class IssuesDiffUtil constructor(private val old: List<Issue>, private val new: List<Issue>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition].id == new[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
}