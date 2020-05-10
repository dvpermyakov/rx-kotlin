package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class JustObservable<T>(
    private val item: T
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observer.onNext(item)
        observer.onComplete()
    }
}