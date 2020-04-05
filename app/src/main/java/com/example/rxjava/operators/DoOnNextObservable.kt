package com.example.rxjava.operators

import com.example.rxjava.functions.ApplyFunction
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class DoOnNextObservable<T>(
    private val observable: Observable<T>,
    private val function: ApplyFunction<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(DoOnNextObserver(observer, function))
    }

    class DoOnNextObserver<T>(
        private val observer: Observer<T>,
        private val function: ApplyFunction<T>
    ) : Observer<T>() {
        override fun onNext(item: T) {
            function.apply(item)
            observer.onNext(item)
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}