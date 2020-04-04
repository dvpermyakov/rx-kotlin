package com.example.rxjava.operators

import com.example.rxjava.functions.FlatMapFunction
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class FlatMapObservable<T>(
    private val observable: Observable<T>,
    private val function: FlatMapFunction<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(FlatMapObserver(observer, function))
    }

    class FlatMapObserver<T>(
        private val observer: Observer<T>,
        private val mapping: FlatMapFunction<T>
    ) : Observer<T>() {
        override fun onNextActual(item: T) {
            mapping.map(item).subscribe(object : Observer<T>() {
                override fun onNextActual(item: T) {
                    observer.onNext(item)
                }

                override fun onComplete() {
                }

                override fun onError(t: Throwable) {
                    observer.onError(t)
                }
            })
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}