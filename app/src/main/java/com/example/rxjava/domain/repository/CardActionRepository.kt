package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.CardAction
import com.example.rxjava.emitter.Emitter
import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.create
import kotlin.random.Random

class CardActionRepository {
    fun getCardAction(): Observable<CardAction> {
        return Observable.create(object : EmitterSource<CardAction> {
            override fun subscribe(emitter: Emitter<CardAction>) {
                emitter.onNext(CardAction(action = "number = ${Random.nextInt()}"))
                emitter.onNext(CardAction(action = "number = ${Random.nextInt()}"))
                emitter.onComplete()
            }
        })
    }
}