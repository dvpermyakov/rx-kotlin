package com.dvpermyakov.rx.utils

import com.dvpermyakov.rx.observers.Observer
import org.junit.Assert
import java.util.concurrent.LinkedBlockingQueue

open class TestObserver<T> : Observer<T> {
    private var state: Observer.State = Observer.State.Idle
    private val list = LinkedBlockingQueue<T>()

    override fun onSubscribe() {
        state = Observer.State.Subscribed
    }

    override fun onNext(item: T) {
        if (state == Observer.State.Subscribed) {
            list.add(item)
        } else {
            throw IllegalStateException("onNext with item ($item) in state ($state)")
        }
    }

    override fun onComplete() {
        state = Observer.State.Completed
    }

    override fun onError(t: Throwable) {
        state = Observer.State.Error(t)
    }

    fun waitForFinished(): TestObserver<T> {
        while (state == Observer.State.Idle || state is Observer.State.Subscribed) {
            Thread.sleep(100L)
        }
        return this
    }

    fun assertSubscribed(): TestObserver<T> {
        Assert.assertEquals(Observer.State.Subscribed, state)
        return this
    }

    fun assertCompletion(): TestObserver<T> {
        Assert.assertEquals(Observer.State.Completed, state)
        return this
    }

    fun assertError(error: Throwable): TestObserver<T> {
        Assert.assertEquals(Observer.State.Error(error), state)
        return this
    }

    fun assertCount(count: Int): TestObserver<T> {
        Assert.assertEquals(count, list.size)
        return this
    }

    fun assertAtIndex(index: Int, value: T): TestObserver<T> {
        Assert.assertEquals(value, list.elementAt(index))
        return this
    }
}