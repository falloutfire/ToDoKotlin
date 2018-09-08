package com.manny.firstkotlin

import android.database.Cursor

interface CardViewInterface {
    fun updateCards()
    fun getToDoOfCard(card: Card)
    fun addCard()
}