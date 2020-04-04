package com.example.rxjava.operators

import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.functions.*
import com.example.rxjava.functions.Function
import com.example.rxjava.observables.Observable

fun <T> Observable<T>.map(function: MapFunction<T>): Observable<T> {
    return MapObservable(this, function)
}

fun <T> Observable<T>.map(lambda: (T) -> T): Observable<T> {
    return MapObservable(this, lambda.toFunction())
}

fun <T> Observable<T>.flatMap(function: FlatMapFunction<T>): Observable<T> {
    return FlatMapObservable(this, function)
}

fun <T> Observable<T>.distinctUntilChanged(): Observable<T> {
    return DistinctObservable(this)
}

fun <T> Observable<T>.takeLast(count: Int): Observable<T> {
    return TakeLastObservable(this, count)
}

fun <T> Observable<T>.onSubscribe(function: Function): Observable<T> {
    return OnSubscribeObservable(this, function)
}

fun <T> Observable<T>.onSubscribe(lambda: () -> Unit): Observable<T> {
    return OnSubscribeObservable(this, lambda.toFunction())
}

fun <T> Observable.Companion.just(item: T): Observable<T> {
    return JustObservable(item)
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