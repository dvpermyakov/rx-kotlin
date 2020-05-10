package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.functions.ApplyFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class DoOnNextObservable<T>(
    private val observable: Observable<T>,
    private val function: ApplyFunction<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(
            DoOnNextObserver(
                observer,
                function
            )
        )
    }

    class DoOnNextObserver<T>(
        private val observer: Observer<T>,
        private val function: ApplyFunction<T>
    ) : Observer<T> {
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