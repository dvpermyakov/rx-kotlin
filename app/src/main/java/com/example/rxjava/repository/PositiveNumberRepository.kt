package com.example.rxjava.repository

import com.example.rxjava.emitter.Emitter
import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.create

class PositiveNumberRepository {
    fun getNumberObservable(): Observable<Int> {
        return getObservable()
    }

    private fun getObservable(): Observable<Int> {
        return Observable.create(NumberSource())
    }

    class NumberSource : EmitterSource<Int> {
        override fun subscribe(emitter: Emitter<Int>) {
            emitter.onNext(10)
            emitter.onNext(1000)
            emitter.onComplete()
        }
    }
}