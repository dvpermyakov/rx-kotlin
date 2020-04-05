package com.example.rxjava.functions

import com.example.rxjava.observables.Observable

interface ObservableMapFunction<T, R> {
    fun map(item: T): Observable<R>
}

fun <T, R> ((T) -> Observable<R>).toFunction(): ObservableMapFunction<T, R> {
    return object : ObservableMapFunction<T, R> {
        override fun map(item: T): Observable<R> {
            return invoke(item)
        }
    }
}