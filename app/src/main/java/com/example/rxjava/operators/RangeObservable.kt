package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class RangeObservable(
    private val count: Int
) : Observable<Int>() {

    override fun subscribeActual(observer: Observer<Int>) {
        (0..count).forEach { index ->
            observer.onNext(index)
        }
        observer.onComplete()
    }
}