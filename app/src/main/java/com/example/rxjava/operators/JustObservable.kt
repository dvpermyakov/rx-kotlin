package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class JustObservable<T>(
    private val item: T
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observer.onNext(item)
        observer.onComplete()
    }
}