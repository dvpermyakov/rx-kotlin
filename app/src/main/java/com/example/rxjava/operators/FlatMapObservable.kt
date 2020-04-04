package com.example.rxjava.operators

import com.example.rxjava.functions.FlatMapFunction
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class FlatMapObservable<T, R>(
    private val observable: Observable<T>,
    private val function: FlatMapFunction<T, R>
) : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        observable.subscribe(FlatMapObserver(observer, function))
    }

    class FlatMapObserver<T, R>(
        private val observer: Observer<R>,
        private val mapping: FlatMapFunction<T, R>
    ) : Observer<T>() {
        override fun onNext(item: T) {
            mapping.map(item).subscribe(FlatMapInnerObserver(observer))
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }

        class FlatMapInnerObserver<T>(
            private val observer: Observer<T>
        ) : Observer<T>() {
            override fun onNext(item: T) {
                observer.onNext(item)
            }

            override fun onComplete() {
            }

            override fun onError(t: Throwable) {
                observer.onError(t)
            }
        }
    }
}