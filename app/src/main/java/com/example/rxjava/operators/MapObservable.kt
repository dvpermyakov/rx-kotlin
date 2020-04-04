package com.example.rxjava.operators

import com.example.rxjava.functions.MapFunction
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class MapObservable<T>(
    private val observable: Observable<T>,
    private val function: MapFunction<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(MapObserver(observer, function))
    }

    class MapObserver<T>(
        private val observer: Observer<T>,
        private val mapping: MapFunction<T>
    ) : Observer<T>() {
        override fun onNextActual(item: T) {
            observer.onNext(mapping.map(item))
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}