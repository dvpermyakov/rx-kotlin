package com.example.rxjava.domain.interactors

import com.example.rxjava.domain.model.Card
import com.example.rxjava.domain.model.CardAction
import com.example.rxjava.domain.model.Transaction
import com.example.rxjava.domain.repository.CardActionRepository
import com.example.rxjava.domain.repository.CardRepository
import com.example.rxjava.domain.repository.TransactionRepository
import com.example.rxjava.functions.ZipperWithTwo
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.flatMap
import com.example.rxjava.operators.zip

class TransactionInteractor {
    private val cardActionRepository = CardActionRepository()
    private val cardRepository = CardRepository()
    private val transactionRepository = TransactionRepository()

    fun getAllTransactions(): Observable<Transaction> {
        return Observable.zip(
            source1 = cardRepository.getMyCards(),
            source2 = cardActionRepository.getCardAction(),
            zipper = object : ZipperWithTwo<Card, CardAction, CardData> {
                override fun apply(item1: Card, item2: CardAction): CardData {
                    return CardData(item1, item2)
                }

            }
        ).flatMap { cardData ->
            transactionRepository.getTransactions(cardData.card)
        }
    }

    data class CardData(
        val card: Card,
        val action: CardAction
    )
}