package com.example.rxjava.interactors

import com.example.rxjava.domain.model.Transaction
import com.example.rxjava.domain.repository.CardRepository
import com.example.rxjava.domain.repository.TransactionRepository
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.flatMap

class TransactionInteractor {
    private val cardRepository = CardRepository()
    private val transactionRepository = TransactionRepository()

    fun getAllTransactions(): Observable<Transaction> {
        return cardRepository.getCards().flatMap { card ->
            transactionRepository.getTransactions(card)
        }
    }
}