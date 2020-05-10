package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.functions.MapFunction
import com.dvpermyakov.rx.functions.ObservableMapFunction
import com.dvpermyakov.rx.functions.toFunction
import com.dvpermyakov.rx.observables.Observable

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