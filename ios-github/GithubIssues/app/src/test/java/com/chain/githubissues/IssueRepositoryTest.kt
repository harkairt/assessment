package com.chain.githubissues

import com.chain.githubissues.data.GithubWebservice
import com.chain.githubissues.data.IssueRepositoryImpl
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.repository.IssueRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class IssueRepositoryTest {

    private val githubWebservice: GithubWebservice = mock()

    private val issueRepository: IssueRepository = IssueRepositoryImpl(githubWebservice)

    @Test
    fun `should delegate call to webservice with stringified state`() {
        issueRepository.getIssues("author", "repo", IssueState.closed)

        verify(githubWebservice, times(1))
            .getIssues("author", "repo", IssueState.closed.toString())
    }
}