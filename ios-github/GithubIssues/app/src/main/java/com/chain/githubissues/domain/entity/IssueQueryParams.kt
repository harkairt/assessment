package com.chain.githubissues.domain.entity

data class IssueQueryParams(
    val repository: Repository,
    val issueState: IssueState = IssueState.open
)