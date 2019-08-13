package com.chain.githubissues.domain.usecase

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueListParams
import com.chain.githubissues.domain.repository.IssueRepository
import io.reactivex.Single
import javax.inject.Inject

class ListIssuesUseCase @Inject constructor(private val issueRepository: IssueRepository) {

    fun listIssues(issueListParams: IssueListParams): Single<List<Issue>> {
        return issueRepository.getIssues(
            issueListParams.author,
            issueListParams.repo,
            issueListParams.issueState
        )
    }

}