package com.example.rxjava.observables

import android.util.Log
import com.example.rxjava.functions.*
import com.example.rxjava.functions.Function
import com.example.rxjava.observers.Observer
import com.example.rxjava.operators.*

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

    fun map(function: MapFunction<T>): Observable<T> {
        return MapObservable(this, function)
    }

    fun flatMap(function: FlatMapFunction<T>): Observable<T> {
        return FlatMapObservable(this, function)
    }

    fun distinctUntilChanged(): Observable<T> {
        return DistinctObservable(this)
    }

    fun takeLast(count: Int): Observable<T> {
        return TakeLastObservable(this, count)
    }

    fun onSubscribe(function: Function): Observable<T> {
        return OnSubscribeObservable(this, function)
    }

    companion object {
        fun <T> just(item: T): Observable<T> {
            return JustObservable(item)
        }

        fun <T> fromCallable(callable: Callable<T>): Observable<T> {
            return FromCallableObservable(callable)
        }

        fun <T> empty(): Observable<T> {
            return EmptyObservable()
        }

        fun <T> never(): Observable<T> {
            return NeverObservable()
        }

        fun range(count: Int): Observable<Int> {
            return RangeObservable(count)
        }

        fun <T> create(emitter: Emitter<T>): Observable<T> {
            return CreateObservable(emitter)
        }
    }
}