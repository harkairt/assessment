package com.chain.githubissues.presentation.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class DisposingViewModel : ViewModel() {
    private val bag = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }

    fun Disposable.disposeOnCleared() {
        bag.add(this)
    }
}