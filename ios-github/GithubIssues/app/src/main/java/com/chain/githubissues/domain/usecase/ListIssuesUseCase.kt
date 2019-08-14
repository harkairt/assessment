package com.chain.githubissues.domain.usecase

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueQueryParams
import com.chain.githubissues.domain.repository.IssueRepository
import io.reactivex.Single
import javax.inject.Inject

class ListIssuesUseCase @Inject constructor(private val issueRepository: IssueRepository) {

    fun listIssues(queryParams: IssueQueryParams, pageIndex: Int): Single<List<Issue>> {
        return issueRepository.getIssues(
            queryParams.repository.author,
            queryParams.repository.name,
            queryParams.issueState,
            pageIndex
        )
    }

}