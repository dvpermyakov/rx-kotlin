package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observer.TestObserver
import org.junit.Test

class FromListObservableTest {

    @Test
    fun fromList() {
        val values = listOf(10, 20, 30)
        val observer = TestObserver<Int>()
        Observable.fromList(values).subscribe(observer)

        observer
            .assertCount(values.size)
            .assertAtIndex(0, values[0])
            .assertAtIndex(1, values[1])
            .assertAtIndex(2, values[2])
            .assertCompletion()
    }
}