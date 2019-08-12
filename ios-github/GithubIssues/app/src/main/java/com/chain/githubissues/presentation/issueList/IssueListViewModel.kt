package com.chain.githubissues.presentation.issueList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.usecase.ListIssuesUseCase
import com.chain.githubissues.presentation.common.DisposingViewModel
import com.chain.githubissues.util.lazyMap
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class IssueListViewModel @Inject constructor(private val listIssuesUseCase: ListIssuesUseCase) : DisposingViewModel() {

    private val issueList: Map<IssueState, LiveData<List<Issue>>> = lazyMap { issueState ->
        val liveData = MutableLiveData<List<Issue>>()
        when (issueState) {
            IssueState.open -> listIssuesUseCase.listOpenIssues("Alamofire", "Alamofire")
                .postInto(liveData)
                .disposeOnCleared()
            IssueState.closed -> listIssuesUseCase.listClosedIssues("Alamofire", "Alamofire")
                .postInto(liveData)
                .disposeOnCleared()
        }

        return@lazyMap liveData
    }

    var issuesList : LiveData<List<Issue>> = issueList[IssueState.open] ?: error("meh")

    fun requestIssueList(issueState: IssueState){
        issuesList = issueList[issueState] ?: error("meh")
    }


}

private fun <T> Single<T>.postInto(liveData: MutableLiveData<T>): Disposable {
    return this.subscribe({ liveData.postValue(it) },
        { error ->
        })
}
