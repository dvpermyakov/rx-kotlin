package com.example.rxjava.shedulers

interface Scheduler {
    fun schedule(task: Runnable)
}