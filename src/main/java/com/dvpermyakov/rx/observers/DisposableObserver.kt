package com.dvpermyakov.rx.observers

import com.dvpermyakov.rx.errors.RxKotlinException
import com.dvpermyakov.rx.functions.Consumer
import com.dvpermyakov.rx.functions.Function
import com.dvpermyakov.rx.observables.Disposable

class DisposableObserver<T>(
    private val onNext: Consumer<T>,
    private val onError: Consumer<Throwable>,
    private val onComplete: Function
) : Observer<T>, Disposable {

    private var isDisposed = false

    override fun onNext(item: T) {
        if (!isDisposed) {
            try {
                onNext.accept(item)
            } catch (ex: Throwable) {
                onError.accept(ex)
            }
        }
    }

    override fun onComplete() {
        if (!isDisposed) {
            try {
                onComplete.action()
            } catch (ex: Throwable) {
                onError.accept(ex)
            }
        }
    }

    override fun onError(t: Throwable) {
        if (!isDisposed) {
            try {
                onError.accept(t)
            } catch (ex: Throwable) {
                throw RxKotlinException("Can't accept exception $ex")
            }
        }
    }

    override fun dispose() {
        isDisposed = true
    }

    override fun isDisposed(): Boolean {
        return isDisposed
    }

}