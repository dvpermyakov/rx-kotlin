package com.example.rxjava.observables

import android.util.Log
import com.example.rxjava.observers.Observer

open class Observable<T> : Disposable {
    private var observer: Observer<T>? = null

    open fun subscribeActual(observer: Observer<T>) = Unit

    fun subscribe(observer: Observer<T>): Disposable {
        Log.e("Observable", "subscribe = ${this.javaClass.simpleName}")
        Log.e("Observable", "observer = ${observer.javaClass.simpleName}")
        Log.e("Observable", "=================")

        subscribeActual(observer)

        this.observer = observer
        return this
    }

    override fun dispose() {
        Log.e("Observable", "dispose = ${this.javaClass.simpleName}")
        Log.e("Observable", "observer = ${observer?.javaClass?.simpleName}")
        Log.e("Observable", "=================")
        observer = null
    }

    companion object
}