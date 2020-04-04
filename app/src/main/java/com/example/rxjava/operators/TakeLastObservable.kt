package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer
import kotlin.math.min

class TakeLastObservable<T>(
    private val observable: Observable<T>,
    private val count: Int
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(TakeLastObserver(observer, count))
    }

    class TakeLastObserver<T>(
        private val observer: Observer<T>,
        private val count: Int
    ) : Observer<T>() {

        private var list = mutableListOf<T>()

        override fun onNext(item: T) {
            list.add(item)
        }

        override fun onComplete() {
            list.reverse()
            (0..min(count, list.lastIndex)).forEach { index ->
                observer.onNext(list[index])
            }
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}