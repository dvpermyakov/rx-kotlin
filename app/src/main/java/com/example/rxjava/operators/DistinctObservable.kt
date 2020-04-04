package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class DistinctObservable<T>(
    private val observable: Observable<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(DistinctObserver(observer))
    }

    class DistinctObserver<T>(
        private val observer: Observer<T>
    ) : Observer<T>() {

        private var current: T? = null

        override fun onNext(item: T) {
            if (current != item) {
                observer.onNext(item)
            }
            current = item
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}