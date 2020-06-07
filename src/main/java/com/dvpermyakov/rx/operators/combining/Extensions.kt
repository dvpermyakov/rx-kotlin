package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.functions.MapFunction
import com.dvpermyakov.rx.functions.Zipper
import com.dvpermyakov.rx.functions.ZipperWithTwo
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.operators.transforming.concatMap
import com.dvpermyakov.rx.operators.transforming.flatMap

fun <T> Observable.Companion.mergeList(sources: List<Observable<T>>): Observable<T> {
    return fromList(sources).flatMap { source -> source }
}

fun <T> Observable.Companion.concatList(sources: List<Observable<T>>): Observable<T> {
    return fromList(sources).concatMap { source -> source }
}

fun <T, R> Observable.Companion.zipList(
    sources: List<Observable<T>>,
    zipper: Zipper<T, R>
): Observable<R> {
    return ZipObservable(sources, zipper)
}

@Suppress("UNCHECKED_CAST")
fun <T1 : Any, T2 : Any, R> Observable.Companion.zip(
    source1: Observable<T1>,
    source2: Observable<T2>,
    zipper: ZipperWithTwo<T1, T2, R>
): Observable<R> {
    return zipList(listOf(source1 as Observable<Any>, source2 as Observable<Any>), zipper)
}

fun <T, R> Observable.Companion.combineLatestList(
    sources: List<Observable<T>>,
    combiner: MapFunction<List<T>, R>
): Observable<R> {
    return CombineLatestObservable(sources, combiner)
}

@Suppress("UNCHECKED_CAST")
fun <T, T1 : T, T2 : T, R> Observable.Companion.combineLatest(
    source1: Observable<T1>,
    source2: Observable<T2>,
    combiner: MapFunction<List<T>, R>
): Observable<R> {
    return combineLatestList(listOf(source1 as Observable<T>, source2 as Observable<T>), combiner)
}

