package com.example.rxjava.emitter

interface EmitterSource<T> {
    fun subscribe(emitter: Emitter<T>)
}