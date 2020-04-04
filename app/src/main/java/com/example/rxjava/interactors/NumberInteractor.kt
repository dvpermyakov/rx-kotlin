package com.example.rxjava.interactors

import com.example.rxjava.observables.Observable
import com.example.rxjava.repository.PositiveNumberRepository

class NumberInteractor {
    private val positiveRepository = PositiveNumberRepository()

    fun getNumberObservable(): Observable<Int> {
        return positiveRepository.getNumberObservable()
            .distinctUntilChanged()
            .map { number -> number * 2 }
    }
}