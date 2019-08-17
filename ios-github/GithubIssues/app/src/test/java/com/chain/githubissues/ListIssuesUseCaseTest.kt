package com.chain.githubissues

import com.chain.githubissues.domain.entity.IssueQueryParams
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.entity.Repository
import com.chain.githubissues.domain.repository.IssueRepository
import com.chain.githubissues.domain.usecase.ListIssuesUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class ListIssuesUseCaseTest {

    private val issueRepository: IssueRepository = mock()

    private val listIssuesUseCase = ListIssuesUseCase(issueRepository)

    @Test
    fun `should get closed issues from repository`() {
        listIssuesUseCase.listIssues(IssueQueryParams(Repository("author", "repo"), IssueState.closed), 1)

        verify(issueRepository, times(1))
            .getIssues("author", "repo", IssueState.closed)
    }

    @Test
    fun `should get open issues from repository`() {
        listIssuesUseCase.listIssues(IssueQueryParams(Repository("author", "repo"), IssueState.open), 1)

        verify(issueRepository, times(1))
            .getIssues("author", "repo", IssueState.open)
    }
}