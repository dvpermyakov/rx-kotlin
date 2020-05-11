package com.dvpermyakov.rx.utils

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler

sealed class Order {
    object First : Order()
    object Second : Order()
    object Third : Order()
}

fun getOrderObservable(): Observable<Order> {
    return Observable.create<Order> { emitter ->
        emitter.onNext(Order.First)
        Thread.sleep(100L)
        emitter.onNext(Order.Second)
        Thread.sleep(200L)
        emitter.onNext(Order.Third)
        emitter.onComplete()
    }.subscribeOn(ThreadScheduler())
}

fun getStringByOrderObservable(order: Order): Observable<String> {
    return when (order) {
        Order.First -> Observable.create { emitter ->
            emitter.onNext("first_1")  // 0L
            Thread.sleep(1000L)
            emitter.onNext("first_2")  // 1000L
            Thread.sleep(1000L)
            emitter.onNext("first_3")  // 2000L
            emitter.onComplete()
        }
        Order.Second -> Observable.create { emitter ->
            emitter.onNext("second_1")  // 100L
            Thread.sleep(500L)
            emitter.onNext("second_2")  // 600L
            emitter.onComplete()
        }
        Order.Third -> Observable.create { emitter ->
            emitter.onNext("third_1")  // 200L
            Thread.sleep(200L)
            emitter.onNext("third_2")  // 400L
            Thread.sleep(400L)
            emitter.onNext("third_3")  // 800L
            Thread.sleep(1000L)
            emitter.onNext("third_4")  // 1800L
            Thread.sleep(400L)
            emitter.onNext("third_5")  // 2200L
            emitter.onComplete()
        }
    }
}