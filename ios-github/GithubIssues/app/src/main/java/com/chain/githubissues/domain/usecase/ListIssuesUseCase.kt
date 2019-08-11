package com.chain.githubissues.domain.usecase

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.repository.IssueRepository
import io.reactivex.Single
import javax.inject.Inject

class ListIssuesUseCase @Inject constructor(private val issueRepository: IssueRepository) {

    fun listOpenIssues(author: String, repo: String) : Single<List<Issue>> {
        return issueRepository.getIssues(author, repo, IssueState.open)
    }

    fun listClosedIssues(author: String, repo: String) : Single<List<Issue>> {
        return issueRepository.getIssues(author, repo, IssueState.closed)
    }

}