package com.dvpermyakov.rx.emitter

interface EmitterSource<T> {
    fun subscribe(emitter: Emitter<T>)
}