package com.chain.githubissues

import com.chain.githubissues.domain.usecase.ListIssuesUseCase
import com.chain.githubissues.helper.InstantExecutorExtension
import com.chain.githubissues.helper.TestSchedulerExtension
import com.chain.githubissues.presentation.issueList.IssueListViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(value = [InstantExecutorExtension::class, TestSchedulerExtension::class])
class IssueListViewModelTest {

    val listIssueUseCase: ListIssuesUseCase = mock()

    val viewModel = IssueListViewModel(listIssueUseCase)


}