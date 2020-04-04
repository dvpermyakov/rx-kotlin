package com.example.rxjava.interactors

import com.example.rxjava.functions.MapFunction
import com.example.rxjava.observables.Observable
import com.example.rxjava.repository.PositiveNumberRepository

class NumberInteractor {
    private val positiveRepository = PositiveNumberRepository()

    fun getNumberObservable(): Observable<Int> {
        return positiveRepository.getNumberObservable()
            .distinctUntilChanged()
            .map(object : MapFunction<Int> {
                override fun map(item: Int): Int {
                    return item * 2
                }
            })
    }
}