package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.emitter.EmitterSource
import com.dvpermyakov.rx.functions.*
import com.dvpermyakov.rx.functions.Function
import com.dvpermyakov.rx.functions.toFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.*
import com.dvpermyakov.rx.operators.transforming.*
import com.dvpermyakov.rx.shedulers.Scheduler

fun <T> Observable<T>.delay(delayMs: Long): Observable<T> {
    return DelayObservable(this, delayMs)
}

fun <T> Observable<T>.doOnNext(function: ApplyFunction<T>): Observable<T> {
    return DoOnNextObservable(this, function)
}

fun <T> Observable<T>.doOnNext(lambda: (T) -> Unit): Observable<T> {
    return DoOnNextObservable(this, lambda.toFunction())
}

fun <T> Observable<T>.observeOn(scheduler: Scheduler): Observable<T> {
    return ObserveOnObservable(this, scheduler)
}

fun <T> Observable<T>.subscribeOn(scheduler: Scheduler): Observable<T> {
    return SubscribeOnObservable(this, scheduler)
}

fun <T> Observable<T>.buffer(count: Int): Observable<List<T>> {
    return BufferObservable(this, count)
}

fun <T, R> Observable<T>.map(function: MapFunction<T, R>): Observable<R> {
    return MapObservable(this, function)
}

fun <T, R> Observable<T>.map(lambda: (T) -> R): Observable<R> {
    return MapObservable(this, lambda.toFunction())
}

fun <T, R> Observable<T>.flatMap(function: ObservableMapFunction<T, R>): Observable<R> {
    return FlatMapObservable(this, function)
}

fun <T, R> Observable<T>.flatMap(lambda: (T) -> Observable<R>): Observable<R> {
    return FlatMapObservable(this, lambda.toFunction())
}

fun <T, R> Observable<T>.concatMap(function: ObservableMapFunction<T, R>): Observable<R> {
    return ConcatMapObservable(this, function)
}

fun <T, R> Observable<T>.concatMap(lambda: (T) -> Observable<R>): Observable<R> {
    return ConcatMapObservable(this, lambda.toFunction())
}

fun <T, R> Observable<T>.switchMap(function: ObservableMapFunction<T, R>): Observable<R> {
    return SwitchMapObservable(this, function)
}

fun <T, R> Observable<T>.switchMap(lambda: (T) -> Observable<R>): Observable<R> {
    return SwitchMapObservable(this, lambda.toFunction())
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

fun <T> Observable<T>.doOnSubscribe(function: Function): Observable<T> {
    return DoOnSubscribeObservable(this, function)
}

fun <T> Observable<T>.doOnSubscribe(lambda: () -> Unit): Observable<T> {
    return DoOnSubscribeObservable(this, lambda.toFunction())
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

fun <T> Observable.Companion.concatList(sources: List<Observable<T>>): Observable<T> {
    return fromList(sources).concatMap { source -> source }
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