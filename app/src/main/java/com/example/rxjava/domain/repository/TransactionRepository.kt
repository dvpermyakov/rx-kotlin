package com.example.rxjava.domain.repository

import com.example.rxjava.domain.model.Card
import com.example.rxjava.domain.model.Transaction
import com.example.rxjava.emitter.Emitter
import com.example.rxjava.emitter.EmitterSource
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.create
import kotlin.random.Random

class TransactionRepository {
    fun getTransactions(card: Card): Observable<Transaction> {
        return Observable.create(
            object : EmitterSource<Transaction> {
                override fun subscribe(emitter: Emitter<Transaction>) {
                    when (card) {
                        Card.MY_CARD_1 -> {
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_1)
                            Thread.sleep(Random.nextLong(3000))
                        }
                        Card.MY_CARD_2 -> {
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_2)
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_3)
                        }
                        Card.MY_CARD_3 -> {
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_4)
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_5)
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_6)
                        }
                        Card.MY_CARD_4 -> {
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_7)
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_8)
                            Thread.sleep(Random.nextLong(3000))
                            emitter.onNext(Transaction.TRANSACTION_9)
                        }
                        Card.OTHER_CARD_5 -> {
                            emitter.onNext(Transaction.TRANSACTION_10)
                        }
                        Card.OTHER_CARD_6 -> {
                            emitter.onNext(Transaction.TRANSACTION_11)
                        }
                        Card.OTHER_CARD_7 -> {
                            emitter.onNext(Transaction.TRANSACTION_12)
                        }
                        Card.OTHER_CARD_8 -> {
                            emitter.onNext(Transaction.TRANSACTION_13)
                        }
                    }
                    emitter.onComplete()
                }
            })
    }
}