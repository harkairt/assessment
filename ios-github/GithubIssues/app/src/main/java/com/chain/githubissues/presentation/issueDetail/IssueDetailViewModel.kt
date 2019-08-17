package com.chain.githubissues.presentation.issueDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.usecase.IssueDetailUseCase
import com.chain.githubissues.presentation.common.DisposingViewModel
import com.chain.githubissues.presentation.common.IssueIdentifier
import com.chain.githubissues.util.postInto
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class IssueDetailViewModel @Inject constructor(private val issueDetailUseCase: IssueDetailUseCase) :
    DisposingViewModel() {
    private var _issueLiveData : MutableLiveData<Issue> = MutableLiveData()
    var issueLiveData : LiveData<Issue> = _issueLiveData

    private val _loadingInProgress: MutableLiveData<Boolean> = MutableLiveData()
    val loadingInProgress: LiveData<Boolean> = _loadingInProgress

    fun requestIssue(issueIdentifier: IssueIdentifier) {
        _loadingInProgress.postValue(true)
        issueDetailUseCase.getIssue(issueIdentifier)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                _loadingInProgress.postValue(false)
            }
            .subscribe(
                {
                    _issueLiveData.postValue(it)
                },
                {
                    Log.e("___", it.localizedMessage)
                })
            .disposeOnCleared()
    }

}