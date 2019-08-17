package com.chain.githubissues.domain.entity

import com.chain.githubissues.util.agoFormat
import java.time.LocalDateTime

open class Issue(
    val body: String? = "",
    val created_at: LocalDateTime = LocalDateTime.now(),
    val closed_at: LocalDateTime? = null,
    val id: Int? = 0,
    val labels: List<Label>? = listOf(),
    val labels_url: String? = "",
    val number: Int? = 0,
    val state: IssueState = IssueState.open,
    val title: String? = "",
    val user: User? = null,
    val url: String? = ""
) {
    val relevanteDateInformation: String
        get() = if (closed_at == null)
            openedAgoFormat
        else closedAgoFormat!!
    private val openedAgoFormat: String get() = "opened ${created_at.agoFormat}"
    private val closedAgoFormat: String?
        get() = closed_at?.let {
            "closed ${closed_at.agoFormat}"
        }
}
