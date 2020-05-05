package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class BufferObservable<T>(
    private val observable: Observable<T>,
    private val count: Int
) : Observable<List<T>>() {

    override fun subscribeActual(observer: Observer<List<T>>) {
        observable.subscribe(BufferObserver(observer, count))
    }

    class BufferObserver<T>(
        private val observer: Observer<List<T>>,
        private val count: Int
    ) : Observer<T> {

        private val list = mutableListOf<T>()

        override fun onNext(item: T) {
            list.add(item)
            if (list.size >= count) {
                observer.onNext(list.subList(0, count))
                list.clear()
            }
        }

        override fun onComplete() {
            if (list.isNotEmpty()) {
                observer.onNext(list)
            }
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}