package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.Card
import com.example.rxjava.emitter.Emitter
import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.create
import kotlin.random.Random

class CardRepository {
    fun getMyCards(): Observable<Card> {
        return Observable.create(
            object : EmitterSource<Card> {
                override fun subscribe(emitter: Emitter<Card>) {
                    emitter.onNext(Card.MY_CARD_1)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onNext(Card.MY_CARD_2)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onNext(Card.MY_CARD_3)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onNext(Card.MY_CARD_4)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onComplete()
                }
            }
        )
    }

    fun getOtherCards(): Observable<Card> {
        return Observable.create(
            object : EmitterSource<Card> {
                override fun subscribe(emitter: Emitter<Card>) {
                    emitter.onNext(Card.OTHER_CARD_5)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onNext(Card.OTHER_CARD_6)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onNext(Card.OTHER_CARD_7)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onNext(Card.OTHER_CARD_8)
                    Thread.sleep(Random.nextLong(3000))
                    emitter.onComplete()
                }
            }
        )
    }

}