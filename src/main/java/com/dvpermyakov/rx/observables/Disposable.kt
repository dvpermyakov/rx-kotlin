package com.dvpermyakov.rx.observables

interface Disposable {
    fun dispose()
    fun isDisposed(): Boolean
}