package com.example.rxjava.functions

interface Function {
    fun action()
}

fun (() -> Unit).toFunction(): Function {
    return object : Function {
        override fun action() {
            invoke()
        }
    }
}