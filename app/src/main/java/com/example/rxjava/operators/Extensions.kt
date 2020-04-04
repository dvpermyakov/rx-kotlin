package com.example.rxjava.operators

import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.functions.*
import com.example.rxjava.functions.Function
import com.example.rxjava.observables.Observable

fun <T> Observable<T>.buffer(count: Int): Observable<List<T>> {
    return BufferObservable(this, count)
}

fun <T, R> Observable<T>.map(function: MapFunction<T, R>): Observable<R> {
    return MapObservable(this, function)
}

fun <T, R> Observable<T>.map(lambda: (T) -> R): Observable<R> {
    return MapObservable(this, lambda.toFunction())
}

fun <T, R> Observable<T>.flatMap(function: FlatMapFunction<T, R>): Observable<R> {
    return FlatMapObservable(this, function)
}

fun <T, R> Observable<T>.flatMap(lambda: (T) -> Observable<R>): Observable<R> {
    return FlatMapObservable(this, lambda.toFunction())
}

fun <T> Observable<T>.distinctUntilChanged(): Observable<T> {
    return DistinctObservable(this)
}

fun <T> Observable<T>.filter(filterFunction: MapFunction<T, Boolean>): Observable<T> {
    return FilterObservable(this, filterFunction)
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

fun <T> Observable.Companion.mergeList(sources: List<Observable<T>>): Observable<T> {
    return fromList(sources).flatMap { source -> source }
}

fun <T, R> Observable.Companion.zipList(
    zipper: Zipper<T, R>,
    sources: List<Observable<T>>
): Observable<R> {
    return ZipObservable(sources, zipper)
}

@Suppress("UNCHECKED_CAST")
fun <T1 : Any, T2 : Any, R> Observable.Companion.zip(
    source1: Observable<T1>,
    source2: Observable<T2>,
    zipper: ZipperWithTwo<T1, T2, R>
): Observable<R> {
    return zipList(zipper, listOf(source1 as Observable<Any>, source2 as Observable<Any>))
}