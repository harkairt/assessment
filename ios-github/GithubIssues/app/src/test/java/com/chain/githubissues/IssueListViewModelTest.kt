package com.chain.githubissues

import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueQueryParams
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.entity.Repository
import com.chain.githubissues.domain.usecase.ListIssuesUseCase
import com.chain.githubissues.helper.InstantExecutorExtension
import com.chain.githubissues.helper.TestSchedulerExtension
import com.chain.githubissues.presentation.issueList.IssueListViewModel
import com.chain.githubissues.util.PagerPool
import com.chain.githubissues.util.PagerPoolFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(value = [InstantExecutorExtension::class, TestSchedulerExtension::class])
class IssueListViewModelTest {

    private val listIssueUseCaseMock: ListIssuesUseCase = mock()
    private val pagerPoolFactoryMock: PagerPoolFactory = mock()
    private val pagerPoolMock: PagerPool<IssueQueryParams, Issue> = mock()

    private val repository = Repository("testAuthor", "testName")

    private val viewModel = IssueListViewModel(listIssueUseCaseMock, pagerPoolFactoryMock)

    @BeforeEach
    fun before(){
        val nextPageLoaderCaptor = argumentCaptor<(IssueQueryParams, Int) -> (Single<List<Issue>>)>()

//        whenever(pagerPoolFactoryMock.createPagerPool(any(), nextPageLoaderCaptor.capture()))
//            .thenReturn(pagerPoolMock)
        whenever(pagerPoolFactoryMock.createPagerPool(any(), anyOrNull<(IssueQueryParams, Int) -> (Single<List<Issue>>)>()))
            .thenAnswer { pagerPoolMock}
    }

    @Test
    fun `should create pager pool with factory`() {
        whenever(pagerPoolFactoryMock.createPagerPool(any(), anyOrNull<(IssueQueryParams, Int) -> (Single<List<Issue>>)>()))
            .thenAnswer { pagerPoolMock}

        val nextPageLoaderCaptor = argumentCaptor<(IssueQueryParams, Int) -> (Single<List<Issue>>)>()

        verify(pagerPoolFactoryMock, times(1))
            .createPagerPool(50, nextPageLoaderCaptor.capture())
    }


    @Test
    fun `should request issues from pagerPool`() {
        viewModel.requestIssuesOf(repository, IssueState.closed)

        verify(pagerPoolMock, times(1)).requestCurrentPages(argForWhich {
            repository == this@IssueListViewModelTest.repository && issueState == IssueState.closed
        })
    }


    @Test
    fun `should request with another page from pagerPool with previously used params`() {
        viewModel.requestIssuesOf(repository, IssueState.closed)
        viewModel.requestAdditionalIssues()
        viewModel.requestAdditionalIssues()

        verify(pagerPoolMock, times(2)).requestWithNextPage(argForWhich {
            repository == this@IssueListViewModelTest.repository && issueState == IssueState.closed
        })
    }

    @Test
    fun `should post loading in progress then not anymore`() {
        val postedLoadingState = mutableListOf<Boolean>()
        viewModel.loadingInProgress.observeForever { postedLoadingState.add(it) }

        viewModel.requestIssuesOf(repository, IssueState.closed)

        assert(postedLoadingState[0])
        assert(postedLoadingState[1] == false)
    }

}