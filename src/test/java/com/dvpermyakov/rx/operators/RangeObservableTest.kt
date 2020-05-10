package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observer.TestObserver
import org.junit.Test

class RangeObservableTest {

    @Test
    fun range() {
        val observer = TestObserver<Int>()
        Observable.range(5).subscribe(observer)

        observer
            .assertCount(5)
            .assertAtIndex(0, 0)
            .assertAtIndex(1, 1)
            .assertAtIndex(2, 2)
            .assertAtIndex(3, 3)
            .assertAtIndex(4, 4)
            .assertCompletion()
    }
}