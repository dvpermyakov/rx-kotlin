package com.dvpermyakov.rx.subjects

import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test
import java.lang.IllegalStateException

class AsyncSubjectTest {

    @Test
    fun asyncSubjectLastValue() {
        val subject = AsyncSubject<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)

        val observer = TestObserver<Int>()
        subject.subscribe(observer)

        subject.onComplete()

        observer
            .assertCompletion()
            .assertCount(1)
            .assertAtIndex(0, 3)
    }

    @Test
    fun asyncSubjectEmpty() {
        val error = IllegalStateException()

        val subject = AsyncSubject<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)
        subject.onError(error)

        val observer = TestObserver<Int>()
        subject.subscribe(observer)

        observer
            .assertError(error)
            .assertCount(0)
    }

}