package com.example.rxjava.ui

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava.R
import com.example.rxjava.domain.interactors.TransactionInteractor
import com.example.rxjava.observables.Disposable
import com.example.rxjava.observers.Observer
import com.example.rxjava.operators.map
import com.example.rxjava.operators.observeOn
import com.example.rxjava.shedulers.MainScheduler
import com.example.rxjava.shedulers.ThreadScheduler

class MainActivity : AppCompatActivity() {
    private val interactor = TransactionInteractor()
    private var disposable: Disposable? = null
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
    }

    override fun onStart() {
        super.onStart()

        disposable = interactor.getAllTransactions()
            .observeOn(ThreadScheduler())
            .map { transaction ->
                "I received a new transaction $transaction"
            }
            .observeOn(MainScheduler())
            .subscribe(MyActivityObserver())
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
        disposable = null
    }

    class MyActivityObserver : Observer<String> {
        override fun onNext(item: String) {
            Log.e(TAG, "onNext = $item")
        }

        override fun onComplete() {
            Log.e(TAG, "onComplete")
        }

        override fun onError(t: Throwable) {
            Log.e(TAG, "error = $t")
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
