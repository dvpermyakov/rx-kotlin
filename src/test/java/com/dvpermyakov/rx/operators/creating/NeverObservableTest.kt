package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test


class NeverObservableTest {

    @Test
    fun never() {
        val observer = TestObserver<Any>()
        Observable.never<Any>().subscribe(observer)

        observer
            .assertCount(0)
            .assertIdle()
    }
}