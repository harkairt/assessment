package com.chain.githubissues.domain.usecase

import com.chain.githubissues.domain.entity.Issue
import io.reactivex.Single

class ListIssuesUseCase {

    fun listOpenIssues(author: String, repo: String) : Single<List<Issue>> {
        TODO()
    }

    fun listClosedIssues(author: String, repo: String) : Single<List<Issue>> {
        TODO()
    }

}