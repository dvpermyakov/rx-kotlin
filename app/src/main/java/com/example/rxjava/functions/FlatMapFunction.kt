package com.example.rxjava.functions

import com.example.rxjava.observables.Observable

interface FlatMapFunction<T, R> {
    fun map(item: T): Observable<R>
}

fun <T, R> ((T) -> Observable<R>).toFunction(): FlatMapFunction<T, R> {
    return object : FlatMapFunction<T, R> {
        override fun map(item: T): Observable<R> {
            return invoke(item)
        }
    }
}