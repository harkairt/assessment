package com.chain.githubissues.util

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val LocalDateTime.agoFormat: String
    get() {
        val duration = Duration.between(this, LocalDateTime.now())
        return when {
            duration.toMinutes() == 1.toLong() -> "1 minute ago"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} minutes ago"
            duration.toHours() == 1.toLong() -> "1 hour ago"
            duration.toHours() < 48 -> "${duration.toHours()} hours ago"
            else -> "on ${this.shortFormat}"
        }
    }

val LocalDateTime.shortFormat: String
    get() = this.format(DateTimeFormatter.ofPattern("MMM dd"))

fun Context.getResourceColor(colorResourceId: Int): Int {
    return ContextCompat.getColor(this, colorResourceId)
}

fun View.getResourceColor(colorResourceId: Int): Int =
    this.context.getResourceColor(colorResourceId)

fun LinearLayout.LayoutParams.setLeftMargin(value: Int) {
    this.setMargins(value, topMargin, rightMargin, bottomMargin)
}

fun LinearLayout.LayoutParams.setRightMargin(value: Int) {
    this.setMargins(leftMargin, topMargin, value, bottomMargin)
}


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun wrappingParams() = LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.WRAP_CONTENT,
    LinearLayout.LayoutParams.WRAP_CONTENT
)

fun <T> Observable<T>.postInto(
    liveData: MutableLiveData<T>,
    onError: (Throwable) -> Unit = {Log.e("___", it.localizedMessage)}
): Disposable {
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

fun <T> Single<T>.postInto(
    liveData: MutableLiveData<T>,
    onError: (Throwable) -> Unit = {Log.e("___", it.localizedMessage)}
): Disposable {
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
