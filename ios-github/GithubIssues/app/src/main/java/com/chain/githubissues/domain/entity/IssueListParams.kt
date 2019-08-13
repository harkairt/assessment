package com.chain.githubissues.domain.entity

data class IssueListParams(
    val author: String,
    val repo: String,
    val issueState: IssueState
)