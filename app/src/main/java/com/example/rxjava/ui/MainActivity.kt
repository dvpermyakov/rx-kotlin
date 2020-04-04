package com.example.rxjava.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava.R
import com.example.rxjava.domain.interactors.TransactionInteractor
import com.example.rxjava.observables.Disposable
import com.example.rxjava.observers.Observer
import com.example.rxjava.operators.map
import com.example.rxjava.operators.doOnSubscribe

class MainActivity : AppCompatActivity() {
    private val interactor = TransactionInteractor()

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        disposable = interactor.getAllTransactions()
            .map { transaction ->
                "I received a new transaction $transaction"
            }
            .doOnSubscribe {
                Log.e("MainActivity", "Create new subscription")
            }
            .subscribe(MyActivityObserver())
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
        disposable = null
    }

    class MyActivityObserver : Observer<String>() {
        override fun onNext(item: String) {
            Log.e("MainActivity", "onNext = $item")
        }

        override fun onComplete() {
            Log.e("MainActivity", "onComplete")
        }

        override fun onError(t: Throwable) {
            Log.e("MainActivity", "error = $t")
        }
    }
}
