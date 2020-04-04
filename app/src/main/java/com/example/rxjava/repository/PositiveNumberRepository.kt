package com.example.rxjava.repository

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class PositiveNumberRepository {
    fun getNumberObservable(): Observable<Int> {
        return getObservable()
    }

    private fun getObservable(): Observable<Int> {
        return object : Observable<Int>() {
            override fun subscribeActual(observer: Observer<Int>) {
                observer.onNext(3)
                observer.onNext(3)
                observer.onNext(3)
                observer.onNext(3)
                observer.onNext(5)
                observer.onNext(5)
                observer.onNext(3)
                observer.onNext(5)
                observer.onNext(5)
                observer.onComplete()
            }
        }
    }
}