package com.chain.githubissues.presentation.issueList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueListParams
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.usecase.ListIssuesUseCase
import com.chain.githubissues.presentation.common.DisposingViewModel
import com.chain.githubissues.util.lazyMap
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import javax.inject.Inject

class IssueListViewModel @Inject constructor(private val listIssuesUseCase: ListIssuesUseCase) : DisposingViewModel() {
    private val initialParams = IssueListParams("square", "retrofit", IssueState.open)

    private val issueList: Map<IssueListParams, LiveData<List<Issue>>> = lazyMap { issueListParams ->
        val liveData = MutableLiveData<List<Issue>>()

        listIssuesUseCase.listIssues(issueListParams)
                .postInto(liveData, onError = {
                    Log.e("", it.localizedMessage)
                })
                .disposeOnCleared()

        liveData
    }

    var issueListLiveData: LiveData<List<Issue>> = issueList.getValue(initialParams)

    fun requestIssueList(issueState: IssueState) {
        issueListLiveData = issueList[IssueListParams(initialParams.author, initialParams.repo, issueState)]
                ?: error("meh")
    }
}


private fun <T> Single<T>.postInto(liveData: MutableLiveData<T>, onError: (Throwable) -> Unit) : Disposable {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    {
                        liveData.postValue(it)
                    },
                    {
                        onError(it)
                    })
}
