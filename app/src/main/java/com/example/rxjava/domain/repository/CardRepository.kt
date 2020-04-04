package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.Card
import com.example.rxjava.emitter.Emitter
import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.create
import com.example.rxjava.operators.fromList

class CardRepository {
    fun getMyCards(): Observable<Card> {
        return Observable.create(
            object : EmitterSource<Card> {
                override fun subscribe(emitter: Emitter<Card>) {
                    emitter.onNext(Card.MY_CARD_1)
                    emitter.onNext(Card.MY_CARD_2)
                    emitter.onNext(Card.MY_CARD_3)
                    emitter.onNext(Card.MY_CARD_4)
                    emitter.onComplete()
                }
            }
        )
    }

    fun getOtherCards(): Observable<Card> {
        return Observable.fromList(
            listOf(Card.OTHER_CARD_5, Card.OTHER_CARD_6, Card.OTHER_CARD_7, Card.OTHER_CARD_8)
        )
    }

}