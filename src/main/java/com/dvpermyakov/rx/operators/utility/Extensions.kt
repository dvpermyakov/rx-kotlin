package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.functions.*
import com.dvpermyakov.rx.functions.Function
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.shedulers.Scheduler

fun <T> Observable<T>.delay(delayMs: Long): Observable<T> {
    return DelayObservable(this, delayMs)
}

fun <T> Observable<T>.doOnNext(function: ApplyFunction<T>): Observable<T> {
    return DoOnNextObservable(this, function)
}

fun <T> Observable<T>.doOnNext(lambda: (T) -> Unit): Observable<T> {
    return doOnNext(lambda.toFunction())
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
    return doOnSubscribe(lambda.toFunction())
}