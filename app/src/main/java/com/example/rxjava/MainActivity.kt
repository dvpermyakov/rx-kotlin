package com.example.rxjava

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava.interactors.NumberInteractor
import com.example.rxjava.observables.Disposable
import com.example.rxjava.observers.Observer
import com.example.rxjava.operators.onSubscribe

class MainActivity : AppCompatActivity() {
    private val interactor = NumberInteractor()

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        disposable = interactor.getNumberObservable()
            .onSubscribe { }
            .subscribe(MyActivityObserver())
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
        disposable = null
    }

    class MyActivityObserver : Observer<Int>() {
        override fun onNextActual(item: Int) {
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
