package com.chain.githubissues.data

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.repository.IssueRepository
import io.reactivex.Single
import javax.inject.Inject

class IssueRepositoryImpl @Inject constructor(private val githubWebservice: GithubWebservice) : IssueRepository{

    override fun getIssues(author: String, repo: String, issueState: IssueState): Single<List<Issue>> {
        return githubWebservice.getIssues(author, repo, issueState.toString())
    }

}