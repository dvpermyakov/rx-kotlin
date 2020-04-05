package com.example.rxjava.shedulers

import android.os.Handler
import android.os.Looper

class MainScheduler : Scheduler {
    override fun schedule(task: Runnable) {
        Handler(Looper.getMainLooper()).post(task)
    }
}