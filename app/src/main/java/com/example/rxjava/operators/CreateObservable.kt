package com.example.rxjava.operators

import com.example.rxjava.emitter.Emitter
import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

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