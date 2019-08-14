package com.chain.githubissues.util

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class Pager<P, R>(
    private val params: P,
    private val pageSize: Int,
    private val loader: (P, Int) -> Single<List<R>>
) {
    private var loadedPageCount = 0
    private val list: MutableList<R> = mutableListOf()
    private val subject: PublishSubject<List<R>> = PublishSubject.create()

    val observableList: Observable<List<R>> = subject
    var onBeforeLoad : () -> Unit = {}
    var onAfterLoad : () -> Unit = {}


    @SuppressLint("CheckResult")
    fun publishListWithNextPage() {
        val hasItemsToLoad = list.count().rem(pageSize) == 0
        if (!hasItemsToLoad)
            return

        var pageToLoad = loadedPageCount.inc()

        onBeforeLoad()
        loader(params, pageToLoad)
            .subscribeOn(Schedulers.io())

            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { onAfterLoad() }
            .subscribe ({
                list.addAll(it)
                subject.onNext(list.toList())
                loadedPageCount = pageToLoad
            },{
                Log.e("","Error loading page: ${it.localizedMessage}")
            }
            )

    }

    fun publishList() {
        if (loadedPageCount > 0)
            subject.onNext(list.toList())
    }
}