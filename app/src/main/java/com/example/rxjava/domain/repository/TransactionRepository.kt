package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.Card
import com.example.rxjava.domain.model.Transaction
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.fromList

class TransactionRepository {
    fun getTransactions(card: Card): Observable<Transaction> {
        return Observable.fromList(
            when (card) {
                Card.MY_CARD_1 -> {
                    listOf(
                        Transaction.TRANSACTION_1
                    )
                }
                Card.MY_CARD_2 -> {
                    listOf(
                        Transaction.TRANSACTION_2,
                        Transaction.TRANSACTION_3
                    )
                }
                Card.MY_CARD_3 -> {
                    listOf(
                        Transaction.TRANSACTION_4,
                        Transaction.TRANSACTION_5,
                        Transaction.TRANSACTION_6
                    )
                }
                Card.MY_CARD_4 -> {
                    listOf(
                        Transaction.TRANSACTION_7,
                        Transaction.TRANSACTION_8,
                        Transaction.TRANSACTION_9
                    )
                }
                Card.OTHER_CARD_5 -> {
                    listOf(
                        Transaction.TRANSACTION_10
                    )
                }
                Card.OTHER_CARD_6 -> {
                    listOf(
                        Transaction.TRANSACTION_11
                    )
                }
                Card.OTHER_CARD_7 -> {
                    listOf(
                        Transaction.TRANSACTION_12
                    )
                }
                Card.OTHER_CARD_8 -> {
                    listOf(
                        Transaction.TRANSACTION_13
                    )
                }
                else -> emptyList()
            }
        )
    }
}