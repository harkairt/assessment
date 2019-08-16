package com.chain.githubissues.helper

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.entity.Label
import com.chain.githubissues.domain.entity.User
import java.time.LocalDateTime

class TestIssue (
    body: String = "",
    created_at: LocalDateTime = LocalDateTime.now(),
    closed_at: LocalDateTime? = null,
    id: Int = 0,
    labels: List<Label> = listOf(),
    labels_url: String = "",
    number: Int = 0,
    state: IssueState = IssueState.open,
    title: String = "",
    user: User = User("", "", 0, ""),
    url: String = ""
) : Issue(body, created_at, closed_at, id, labels, labels_url, number, state, title, user, url) {
}
