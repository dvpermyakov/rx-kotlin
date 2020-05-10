package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Ignore
import org.junit.Test

class MergeObservableTest {

    @Ignore
    @Test
    fun mergeList() {
        val values1 = listOf(1, 2, 3)
        val values2 = listOf(4, 5, 6)
        val values = values1 + values2

        val observer = TestObserver<Int>()
        Observable.mergeList(
            listOf(
                Observable.fromList(values1),
                Observable.fromList(values2)
            )
        ).subscribe(observer)

        observer
            .assertCount(values.size)
            .assertAtIndex(0, values[0])
            .assertAtIndex(1, values[1])
            .assertAtIndex(2, values[2])
            .assertAtIndex(3, values[3])
            .assertAtIndex(4, values[4])
            .assertAtIndex(5, values[5])
            .assertCompletion()
    }

}