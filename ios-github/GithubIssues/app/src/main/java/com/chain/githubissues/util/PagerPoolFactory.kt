package com.chain.githubissues.util

import io.reactivex.Single
import javax.inject.Inject

class PagerPoolFactory @Inject constructor() {

    fun <P, R> createPagerPool(pageSize: Int, loader: (P, Int) -> Single<List<R>>) : PagerPool<P, R> {
        return PagerPool(pageSize, loader)
    }

}