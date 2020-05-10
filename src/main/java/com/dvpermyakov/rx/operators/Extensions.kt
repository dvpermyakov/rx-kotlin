package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.functions.*
import com.dvpermyakov.rx.functions.Function
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
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

fun <T> Observable<T>.doOnSubscribe(function: Function): Observable<T> {
    return DoOnSubscribeObservable(this, function)
}

fun <T> Observable<T>.doOnSubscribe(lambda: () -> Unit): Observable<T> {
    return DoOnSubscribeObservable(this, lambda.toFunction())
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