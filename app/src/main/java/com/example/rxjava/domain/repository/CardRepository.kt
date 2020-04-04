package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.Card
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.fromList

class CardRepository {
    fun getCards(): Observable<Card> {
        return Observable.fromList(
            listOf(Card.CARD_1, Card.CARD_2, Card.CARD_3, Card.CARD_4)
        )
    }
}