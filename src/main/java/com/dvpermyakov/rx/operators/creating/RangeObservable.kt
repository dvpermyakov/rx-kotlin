package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class RangeObservable(
    private val count: Int
) : Observable<Int>() {

    override fun subscribeActual(observer: Observer<Int>) {
        (0 until count).forEach { index ->
            observer.onNext(index)
        }
        observer.onComplete()
    }
}