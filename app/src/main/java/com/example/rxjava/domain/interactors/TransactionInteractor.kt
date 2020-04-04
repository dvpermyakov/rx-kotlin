package com.example.rxjava.domain.interactors

import com.example.rxjava.domain.model.Transaction
import com.example.rxjava.domain.repository.CardRepository
import com.example.rxjava.domain.repository.TransactionRepository
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.flatMap
import com.example.rxjava.operators.merge

class TransactionInteractor {
    private val cardRepository = CardRepository()
    private val transactionRepository = TransactionRepository()

    fun getAllTransactions(): Observable<Transaction> {
        return Observable.merge(
            cardRepository.getMyCards(),
            cardRepository.getOtherCards()
        ).flatMap { card ->
            transactionRepository.getTransactions(card)
        }
    }
}