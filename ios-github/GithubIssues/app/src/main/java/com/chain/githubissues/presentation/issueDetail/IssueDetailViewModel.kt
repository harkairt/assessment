package com.chain.githubissues.presentation.issueDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chain.githubissues.domain.entity.Issue
import com.chain.githubissues.domain.usecase.IssueDetailUseCase
import com.chain.githubissues.presentation.common.DisposingViewModel
import com.chain.githubissues.presentation.common.IssueIdentifier
import com.chain.githubissues.util.postInto
import javax.inject.Inject

class IssueDetailViewModel @Inject constructor(private val issueDetailUseCase: IssueDetailUseCase) :
    DisposingViewModel() {


    private var _issueLiveData : MutableLiveData<Issue> = MutableLiveData()
    var issueLiveData : LiveData<Issue> = _issueLiveData

    fun requestIssue(issueIdentifier: IssueIdentifier) {
        issueDetailUseCase.getIssue(issueIdentifier)
            .postInto(_issueLiveData)
            .disposeOnCleared()
    }

}