package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.functions.MapFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

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