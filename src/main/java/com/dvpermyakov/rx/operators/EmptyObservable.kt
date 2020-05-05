package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class EmptyObservable<T> : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observer.onComplete()
    }
}