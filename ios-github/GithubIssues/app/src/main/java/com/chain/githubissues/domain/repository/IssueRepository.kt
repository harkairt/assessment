package com.chain.githubissues.domain.repository

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueState
import io.reactivex.Single

interface IssueRepository {

    fun getIssues(author: String, repo: String, issueState: IssueState, page: Int = 1): Single<List<Issue>>

}