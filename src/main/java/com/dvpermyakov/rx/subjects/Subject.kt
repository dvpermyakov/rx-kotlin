package com.dvpermyakov.rx.subjects

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

abstract class Subject<T> : Observable<T>(), Observer<T>