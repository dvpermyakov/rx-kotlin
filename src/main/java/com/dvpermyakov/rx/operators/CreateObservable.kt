package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.emitter.Emitter
import com.dvpermyakov.rx.emitter.EmitterSource
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class CreateObservable<T>(
    private val source: EmitterSource<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        source.subscribe(EmitterObserver(observer))
    }

    class EmitterObserver<T>(
        private val observer: Observer<T>
    ) : Emitter<T> {
        override fun onNext(item: T) {
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