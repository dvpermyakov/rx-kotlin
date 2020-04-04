package com.example.rxjava.functions

import com.example.rxjava.observables.Observable

interface FlatMapFunction<T> {
    fun map(item: T): Observable<T>
}