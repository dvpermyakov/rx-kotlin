package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observer.TestObserver
import org.junit.Test

class EmptyObservableTest {

    @Test
    fun empty() {
        val observer = TestObserver<Any>()
        Observable.empty<Any>().subscribe(observer)

        observer
            .assertCount(0)
            .assertCompletion()
    }
}