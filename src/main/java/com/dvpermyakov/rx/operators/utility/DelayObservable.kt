package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class DelayObservable<T>(
    private val observable: Observable<T>,
    private val delayMs: Long
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(
            DelayObserver(
                observer,
                delayMs
            )
        )
    }

    class DelayObserver<T>(
        private val observer: Observer<T>,
        private val delayMs: Long
    ) : Observer<T> {

        override fun onNext(item: T) {
            Thread.sleep(delayMs)
            observer.onNext(item)
        }

        override fun onComplete() {
            Thread.sleep(delayMs)
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            Thread.sleep(delayMs)
            observer.onError(t)
        }
    }
}