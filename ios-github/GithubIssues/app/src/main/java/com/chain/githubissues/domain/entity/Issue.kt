package com.chain.githubissues.domain.entity

data class Issue(
    val body: String?,
    val created_at: String?,
    val id: Int?,
    val labels: List<Any?>?,
    val labels_url: String?,
    val number: Int?,
    val state: String?,
    val title: String?,
    val user: User?
) {
}