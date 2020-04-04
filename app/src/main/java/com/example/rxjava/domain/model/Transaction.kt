package com.example.rxjava.domain.model

data class Transaction(
    val id: String
) {
    companion object {
        val TRANSACTION_1 = Transaction("transaction_id_1")
        val TRANSACTION_2 = Transaction("transaction_id_2")
        val TRANSACTION_3 = Transaction("transaction_id_3")
        val TRANSACTION_4 = Transaction("transaction_id_4")
        val TRANSACTION_5 = Transaction("transaction_id_5")
        val TRANSACTION_6 = Transaction("transaction_id_6")
        val TRANSACTION_7 = Transaction("transaction_id_7")
        val TRANSACTION_8 = Transaction("transaction_id_8")
        val TRANSACTION_9 = Transaction("transaction_id_9")
    }
}