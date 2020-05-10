package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.emitter.EmitterSource
import com.dvpermyakov.rx.functions.Callable
import com.dvpermyakov.rx.functions.toCallable
import com.dvpermyakov.rx.observables.Observable

fun <T> Observable.Companion.just(item: T): Observable<T> {
    return JustObservable(item)
}

fun <T> Observable.Companion.fromList(items: List<T>): Observable<T> {
    return FromListObservable(items)
}

fun <T> Observable.Companion.fromCallable(callable: Callable<T>): Observable<T> {
    return FromCallableObservable(callable)
}

fun <T> Observable.Companion.fromCallable(lambda: () -> T): Observable<T> {
    return FromCallableObservable(lambda.toCallable())
}

fun <T> Observable.Companion.empty(): Observable<T> {
    return EmptyObservable()
}

fun <T> Observable.Companion.never(): Observable<T> {
    return NeverObservable()
}

fun Observable.Companion.range(count: Int): Observable<Int> {
    return RangeObservable(count)
}

fun <T> Observable.Companion.create(source: EmitterSource<T>): Observable<T> {
    return CreateObservable(source)
}