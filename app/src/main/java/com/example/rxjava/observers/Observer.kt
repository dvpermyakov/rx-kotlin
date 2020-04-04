package com.example.rxjava.observers

import android.util.Log

abstract class Observer<T> {

    fun onNext(item: T) {
        Log.e("Observer", "(${item}) onNext = ${this.javaClass.simpleName}")
        Log.e("Observer", "=================")

        onNextActual(item)
    }

    abstract fun onNextActual(item: T)

    abstract fun onComplete()

    abstract fun onError(t: Throwable)

}