package com.dvpermyakov.rx.subjects

import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class ReplaySubjectTest {

    @Test
    fun replaySubject() {
        val subject = ReplaySubject<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)

        val observer1 = TestObserver<Int>()
        subject.subscribe(observer1)

        subject.onNext(4)

        val observer2 = TestObserver<Int>()
        subject.subscribe(observer2)

        subject.onNext(5)
        subject.onComplete()

        observer1
            .assertCompletion()
            .assertCount(5)
            .assertAtIndex(0, 1)
            .assertAtIndex(1, 2)
            .assertAtIndex(2, 3)
            .assertAtIndex(3, 4)
            .assertAtIndex(4, 5)

        observer2
            .assertCompletion()
            .assertCount(5)
            .assertAtIndex(0, 1)
            .assertAtIndex(1, 2)
            .assertAtIndex(2, 3)
            .assertAtIndex(3, 4)
            .assertAtIndex(4, 5)
    }

}