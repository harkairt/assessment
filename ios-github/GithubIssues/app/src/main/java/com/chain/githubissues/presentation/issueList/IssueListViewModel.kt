package com.chain.githubissues.presentation.issueList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.entity.IssueListParams
import com.chain.githubissues.domain.entity.IssueState
import com.chain.githubissues.domain.usecase.ListIssuesUseCase
import com.chain.githubissues.presentation.common.DisposingViewModel
import com.chain.githubissues.util.postInto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class IssueListViewModel @Inject constructor(private val listIssuesUseCase: ListIssuesUseCase) :
    DisposingViewModel() {
    private val initialParams = IssueListParams("square", "retrofit", IssueState.open)
    private val issueListCache = mutableMapOf<IssueListParams, List<Issue>>()
    val issueListLiveData: MutableLiveData<List<Issue>> = MutableLiveData()

    private val _loadingInProgress: MutableLiveData<Boolean> = MutableLiveData()
    val loadingInProgress: LiveData<Boolean> = _loadingInProgress

    var issueListSource: PublishSubject<List<Issue>> = PublishSubject.create()

    init {
        issueListSource
            .postInto(issueListLiveData, onError = { Log.e("", it.localizedMessage) })
            .disposeOnCleared()

        requestIssueList(initialParams.issueState)
    }

    fun requestIssueList(issueState: IssueState) {
        val param = IssueListParams(initialParams.author, initialParams.repo, issueState)
        if (issueListCache.containsKey(param)) {
            issueListSource.onNext(issueListCache[param]!!)
            return
        }

        _loadingInProgress.postValue(true)
        issueListSource.onNext(listOf())

        listIssuesUseCase.listIssues(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                _loadingInProgress.postValue(false)
            }
            .subscribe({
                issueListSource.onNext(it)
                issueListCache[param] = it
            }, {
                Log.e("", it.localizedMessage)
            }).disposeOnCleared()

    }
}


