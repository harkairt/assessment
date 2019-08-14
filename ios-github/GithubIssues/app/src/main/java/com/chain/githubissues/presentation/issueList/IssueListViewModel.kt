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
import com.chain.githubissues.util.PagerPool
import com.chain.githubissues.util.postInto
import javax.inject.Inject

class IssueListViewModel @Inject constructor(private val listIssuesUseCase: ListIssuesUseCase) :
    DisposingViewModel() {
    private var queryParams: IssueQueryParams = IssueQueryParams(Repository("square", "retrofit"))
    val issueListLiveData: MutableLiveData<List<Issue>> = MutableLiveData()

    private val _loadingInProgress: MutableLiveData<Boolean> = MutableLiveData()
    val loadingInProgress: LiveData<Boolean> = _loadingInProgress

    private val pagerPool =
        PagerPool<IssueQueryParams, Issue>(pageSize) { params, pageNumber ->
            listIssuesUseCase.listIssues(params, pageNumber)
        }.apply {
            onBeforeLoad = {_loadingInProgress.postValue(true)}
            onAfterLoad = {_loadingInProgress.postValue(false)}
            observableList
                .postInto(issueListLiveData)
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


