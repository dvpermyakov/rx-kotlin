package com.dvpermyakov.rx.operators.filtering

import com.dvpermyakov.rx.functions.MapFunction
import com.dvpermyakov.rx.functions.toFunction
import com.dvpermyakov.rx.observables.Observable

fun <T> Observable<T>.distinctUntilChanged(): Observable<T> {
    return DistinctObservable(this)
}

fun <T> Observable<T>.filter(filterFunction: MapFunction<T, Boolean>): Observable<T> {
    return FilterObservable(this, filterFunction)
}

fun <T> Observable<T>.filter(lambda: (T) -> Boolean): Observable<T> {
    return FilterObservable(this, lambda.toFunction())
}

fun <T> Observable<T>.takeLast(count: Int): Observable<T> {
    return TakeLastObservable(this, count)
}