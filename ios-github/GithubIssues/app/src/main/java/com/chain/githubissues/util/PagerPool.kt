package com.chain.githubissues.util

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class PagerPool<P, R>(private val pageSize: Int, private val loader: (P, Int) -> Single<List<R>>) {
    private val pagerMap: Map<P, Pager<P, R>> = lazyMap { param ->
        Pager(param, pageSize, loader).apply {
            onBeforeLoad = this@PagerPool.onBeforeLoad
            onAfterLoad = this@PagerPool.onAfterLoad
            observableList.subscribe(subject)
            publishListWithNextPage()
        }
    }

    private val subject: PublishSubject<List<R>> = PublishSubject.create()
    val observableList: Observable<List<R>> = subject

    var onBeforeLoad: () -> Unit = {}
    var onAfterLoad: () -> Unit = {}

    fun requestWithNextPage(param: P) = pagerMap.getValue(param).publishListWithNextPage()

    fun requestCurrentPages(param: P) = pagerMap.getValue(param).publishList()
}