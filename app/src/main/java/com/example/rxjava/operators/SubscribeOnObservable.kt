package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class SubscribeOnObservable<T>(
    private val observable: Observable<T>,
    private val thread: Thread
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(observer)
    }
}