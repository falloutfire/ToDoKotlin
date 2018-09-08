package com.manny.firstkotlin

import android.content.Context

interface CardPresenterInterface {
    fun addCard(context: Context, card: Card)
    fun addToDo(context: Context, id: Long, toDo: ToDo)
    fun save(context: Context, id: Long, card: Card)
    fun delete(context: Context, id: Long)
    fun getAllCards(context: Context)
}