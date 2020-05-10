package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.utils.TestObserver
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