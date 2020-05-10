package com.dvpermyakov.rx.emitter

interface EmitterSource<T> {
    fun subscribe(emitter: Emitter<T>)
}

fun <T> ((Emitter<T>) -> Unit).toEmitterSource(): EmitterSource<T> {
    return object : EmitterSource<T> {
        override fun subscribe(emitter: Emitter<T>) {
            invoke(emitter)
        }
    }
}