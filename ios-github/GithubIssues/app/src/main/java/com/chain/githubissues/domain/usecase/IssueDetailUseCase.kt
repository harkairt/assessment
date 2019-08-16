package com.chain.githubissues.domain.usecase

import com.chain.githubissues.domain.entity.Repository
import com.chain.githubissues.domain.repository.IssueRepository
import com.chain.githubissues.presentation.common.IssueIdentifier
import javax.inject.Inject

class IssueDetailUseCase @Inject constructor(private val issueRepository: IssueRepository) {

    fun getIssue(issueIdentifier: IssueIdentifier) =
        issueRepository.getIssue(issueIdentifier.author, issueIdentifier.repo, issueIdentifier.issueId)

}