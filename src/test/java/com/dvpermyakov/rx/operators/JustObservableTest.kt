package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observer.TestObserver
import org.junit.Test

class JustObservableTest {

    @Test
    fun just() {
        val value = 10
        val observer = TestObserver<Int>()
        Observable.just(value).subscribe(observer)

        observer
            .assertCount(1)
            .assertAtIndex(0, value)
            .assertCompletion()
    }
}