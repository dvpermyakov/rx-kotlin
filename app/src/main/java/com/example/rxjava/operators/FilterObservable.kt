package com.example.rxjava.operators

import com.example.rxjava.functions.MapFunction
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class FilterObservable<T>(
    private val observable: Observable<T>,
    private val filterFunction: MapFunction<T, Boolean>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(FilterObserver(observer, filterFunction))
    }

    class FilterObserver<T>(
        private val observer: Observer<T>,
        private val filterFunction: MapFunction<T, Boolean>
    ) : Observer<T> {

        override fun onNext(item: T) {
            if (filterFunction.map(item)) {
                observer.onNext(item)
            }
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}