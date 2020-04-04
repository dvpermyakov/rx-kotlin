package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.Card
import com.example.rxjava.domain.model.Transaction
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.fromList

class TransactionRepository {
    fun getTransactions(card: Card): Observable<Transaction> {
        return Observable.fromList(
            when (card) {
                Card.CARD_1 -> listOf(
                    Transaction.TRANSACTION_1
                )
                Card.CARD_2 -> listOf(
                    Transaction.TRANSACTION_2,
                    Transaction.TRANSACTION_3
                )
                Card.CARD_3 -> listOf(
                    Transaction.TRANSACTION_4,
                    Transaction.TRANSACTION_5,
                    Transaction.TRANSACTION_6
                )
                Card.CARD_4 -> listOf(
                    Transaction.TRANSACTION_7,
                    Transaction.TRANSACTION_8,
                    Transaction.TRANSACTION_9
                )
                else -> emptyList()
            }
        )
    }
}