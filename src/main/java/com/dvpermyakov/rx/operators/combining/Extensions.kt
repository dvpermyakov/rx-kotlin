package com.dvpermyakov.rx.operators.combining

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