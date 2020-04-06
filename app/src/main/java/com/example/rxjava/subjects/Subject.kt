package com.example.rxjava.subjects

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer


abstract class Subject<T> : Observable<T>(), Observer<T>