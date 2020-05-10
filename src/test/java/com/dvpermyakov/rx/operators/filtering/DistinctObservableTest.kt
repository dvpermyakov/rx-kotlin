package com.dvpermyakov.rx.operators.filtering

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observer.TestObserver
import com.dvpermyakov.rx.operators.distinctUntilChanged
import com.dvpermyakov.rx.operators.fromList
import org.junit.Test

class DistinctObservableTest {

    @Test
    fun distinct() {
        val values = listOf(0, 0, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 6, 7, 8, 8, 9)
        val observer = TestObserver<Int>()
        Observable
            .fromList(values)
            .distinctUntilChanged()
            .subscribe(observer)

        observer
            .assertCount(10)
            .assertAtIndex(0, 0)
            .assertAtIndex(1, 1)
            .assertAtIndex(2, 2)
            .assertAtIndex(3, 3)
            .assertAtIndex(4, 4)
            .assertAtIndex(5, 5)
            .assertAtIndex(6, 6)
            .assertAtIndex(7, 7)
            .assertAtIndex(8, 8)
            .assertAtIndex(9, 9)
            .assertCompletion()
    }

}