package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.functions.MapFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class MapObservable<T, R>(
    private val observable: Observable<T>,
    private val function: MapFunction<T, R>
) : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        observable.subscribe(MapObserver(observer, function))
    }

    class MapObserver<T, R>(
        private val observer: Observer<R>,
        private val mapping: MapFunction<T, R>
    ) : Observer<T> {
        override fun onNext(item: T) {
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