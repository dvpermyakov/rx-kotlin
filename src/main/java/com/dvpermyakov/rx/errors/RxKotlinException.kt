package com.dvpermyakov.rx.errors

import java.lang.IllegalStateException

data class RxKotlinException(override val message: String) : IllegalStateException(message)