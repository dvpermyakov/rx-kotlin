package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.functions.ZipperWithTwo
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Ignore
import org.junit.Test

class ZipObservableTest {

    @Ignore
    @Test
    fun zip() {
        val valuesInt = listOf(1, 2, 3)
        val valuesString = listOf("a", "b", "c", "d", "e")

        val observer = TestObserver<ZipPair>()
        Observable.zip(
            Observable.fromList(valuesInt),
            Observable.fromList(valuesString),
            object : ZipperWithTwo<Int, String, ZipPair> {
                override fun apply(item1: Int, item2: String) = ZipPair(item1, item2)
            }
        ).subscribe(observer)

        observer
            .assertCount(minOf(valuesInt.size, valuesString.size))
            .assertAtIndex(0, ZipPair(valuesInt[0], valuesString[0]))
            .assertAtIndex(1, ZipPair(valuesInt[1], valuesString[1]))
            .assertAtIndex(2, ZipPair(valuesInt[2], valuesString[2]))
            .assertCompletion()
    }

    data class ZipPair(val int: Int, val string: String)
}