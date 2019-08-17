package com.chain.githubissues.presentation.issueList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chain.githubissues.data.pageSize
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueQueryParams
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.entity.Repository
import com.chain.githubissues.domain.usecase.ListIssuesUseCase
import com.chain.githubissues.presentation.common.DisposingViewModel
import com.chain.githubissues.util.PagerPoolFactory
import com.chain.githubissues.util.postInto
import javax.inject.Inject

class IssueListViewModel @Inject constructor(
    private val listIssuesUseCase: ListIssuesUseCase,
    private val pagerPoolFactory: PagerPoolFactory
) :
    DisposingViewModel() {
    private var queryParams: IssueQueryParams = IssueQueryParams(Repository("square", "retrofit"))
        set(value) {
            field = value
            _activeIssueState.postValue(value.issueState)
        }
    private val _activeIssueState: MutableLiveData<IssueState> =
        MutableLiveData<IssueState>().apply {
            postValue(IssueState.open)
        }
    val activeIssueState: LiveData<IssueState> = _activeIssueState

    private val _issueListLiveData: MutableLiveData<List<Issue>> = MutableLiveData()
    val issueListLiveData: LiveData<List<Issue>> = _issueListLiveData

    private val _loadingInProgress: MutableLiveData<Boolean> = MutableLiveData()
    val loadingInProgress: LiveData<Boolean> = _loadingInProgress


    private val pagerPool =
        pagerPoolFactory.createPagerPool<IssueQueryParams, Issue>(pageSize) { params, pageNumber ->
            listIssuesUseCase.listIssues(params, pageNumber)
        }.apply {
            onBeforeLoad = { _loadingInProgress.postValue(true) }
            onAfterLoad = { _loadingInProgress.postValue(false) }
            observableList
                .postInto(_issueListLiveData)
                .disposeOnCleared()
        }

    fun requestIssuesOf(repo: Repository, issueState: IssueState = IssueState.open) {
        queryParams = IssueQueryParams(repo, issueState)
        pagerPool.requestCurrentPages(queryParams)
    }

    fun requestAdditionalIssues() {
        pagerPool.requestWithNextPage(queryParams)
    }

}


