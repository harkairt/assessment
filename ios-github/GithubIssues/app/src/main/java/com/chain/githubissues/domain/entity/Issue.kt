package com.chain.githubissues.domain.entity

import com.chain.githubissues.util.agoFormat
import java.time.LocalDateTime

class Issue(
    val body: String? = "",
    val created_at: LocalDateTime,
    val closed_at: LocalDateTime? = null,
    val id: Int? = 0,
    val labels: List<Label>? = listOf(),
    val labels_url: String? = "",
    val number: Int? = 0,
    val state: IssueState = IssueState.open,
    val title: String? = "",
    val user: User? = null
) {
    val relevanteDateInformation: String
        get() = if (closed_at == null)
            "opened ${created_at.agoFormat}"
        else "closed ${closed_at.agoFormat}"
}