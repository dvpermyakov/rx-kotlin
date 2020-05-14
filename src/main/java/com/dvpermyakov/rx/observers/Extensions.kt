package com.dvpermyakov.rx.observers

import com.dvpermyakov.rx.functions.toConsumer
import com.dvpermyakov.rx.functions.toFunction
import com.dvpermyakov.rx.observables.Disposable
import com.dvpermyakov.rx.observables.Observable

fun <T> Observable<T>.subscribeLambdas(
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onComplete: () -> Unit
): Disposable {
    return subscribe(
        onNext = onNext.toConsumer(),
        onError = onError.toConsumer(),
        onComplete = onComplete.toFunction()
    )
}