package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.Card
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.fromList

class CardRepository {
    fun getMyCards(): Observable<Card> {
        return Observable.fromList(
            listOf(Card.MY_CARD_1, Card.MY_CARD_2, Card.MY_CARD_3, Card.MY_CARD_4)
        )
    }

    fun getOtherCards(): Observable<Card> {
        return Observable.fromList(
            listOf(Card.OTHER_CARD_5, Card.OTHER_CARD_6, Card.OTHER_CARD_7, Card.OTHER_CARD_8)
        )
    }

}