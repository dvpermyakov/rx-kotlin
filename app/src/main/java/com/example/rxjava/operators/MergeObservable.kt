package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class MergeObservable<T>(
    private val observable1: Observable<T>,
    private val observable2: Observable<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable1.subscribe(observer)
        observable2.subscribe(observer)
    }
}