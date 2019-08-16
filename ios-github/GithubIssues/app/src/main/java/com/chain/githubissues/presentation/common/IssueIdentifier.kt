package com.chain.githubissues.presentation.common

import android.os.Parcelable
import com.chain.githubissues.domain.entity.Issue
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IssueIdentifier(val author: String, val repo: String, val issueId: Int) :
    Parcelable

val Issue.issueIdentifier : IssueIdentifier
get() {
    val urlParts = this.url!!.split('/')
    val urlPartsReversed = urlParts.asReversed()
    val issueId = urlPartsReversed[0].toInt()
    val repo = urlPartsReversed[2]
    val author = urlPartsReversed[3]

    return IssueIdentifier(author, repo, issueId)
}