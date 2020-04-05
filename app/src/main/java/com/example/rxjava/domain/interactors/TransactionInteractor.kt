package com.example.rxjava.domain.interactors

import android.util.Log
import com.example.rxjava.domain.model.Transaction
import com.example.rxjava.domain.repository.CardRepository
import com.example.rxjava.domain.repository.TransactionRepository
import com.example.rxjava.observables.Observable
import com.example.rxjava.operators.doOnNext
import com.example.rxjava.operators.flatMap
import com.example.rxjava.operators.subscribeOn
import com.example.rxjava.shedulers.ThreadScheduler

class TransactionInteractor {
    private val cardRepository = CardRepository()
    private val transactionRepository = TransactionRepository()

    fun getAllTransactions(): Observable<Transaction> {
        return cardRepository.getMyCards()
            .subscribeOn(ThreadScheduler())
            .doOnNext { Log.e(TAG, "card = $it, thread = ${Thread.currentThread()}") }
            .flatMap { card ->
                transactionRepository.getTransactions(card).subscribeOn(ThreadScheduler())
            }
            .doOnNext { Log.e(TAG, "transaction = $it, thread = ${Thread.currentThread()}") }
    }

    companion object {
        private const val TAG = "TransactionInteractor"
    }
}