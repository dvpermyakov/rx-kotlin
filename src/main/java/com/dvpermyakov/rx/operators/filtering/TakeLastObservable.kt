package com.dvpermyakov.rx.operators.filtering

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer
import kotlin.math.min

class TakeLastObservable<T>(
    private val observable: Observable<T>,
    private val count: Int
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(
            TakeLastObserver(
                observer,
                count
            )
        )
    }

    class TakeLastObserver<T>(
        private val observer: Observer<T>,
        private val count: Int
    ) : Observer<T> {

        private var list = mutableListOf<T>()

        override fun onNext(item: T) {
            list.add(item)
        }

        override fun onComplete() {
            val count = min(count, list.lastIndex)
            (count - 1 downTo 0).forEach { index ->
                observer.onNext(list[list.lastIndex - index])
            }
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}