package com.example.rxjava.domain.model

data class Card(
    val id: String
) {
    companion object {
        val MY_CARD_1 = Card("card_id_1")
        val MY_CARD_2 = Card("card_id_2")
        val MY_CARD_3 = Card("card_id_3")
        val MY_CARD_4 = Card("card_id_4")

        val OTHER_CARD_5 = Card("card_id_5")
        val OTHER_CARD_6 = Card("card_id_6")
        val OTHER_CARD_7 = Card("card_id_7")
        val OTHER_CARD_8 = Card("card_id_8")
    }
}