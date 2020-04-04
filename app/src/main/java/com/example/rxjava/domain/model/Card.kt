package com.example.rxjava.domain.model

data class Card(
    val id: String
) {
    companion object {
        val CARD_1 = Card("card_id_1")
        val CARD_2 = Card("card_id_2")
        val CARD_3 = Card("card_id_3")
        val CARD_4 = Card("card_id_4")
    }
}