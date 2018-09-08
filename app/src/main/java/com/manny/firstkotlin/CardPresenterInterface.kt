package com.manny.firstkotlin

import android.content.Context

interface CardPresenterInterface {
    fun addCard(context: Context)
    fun addToDo(context: Context, id: Long, toDo: ToDo)
    fun save(context: Context, id: Long, card: Card)
    fun delete()
    fun getAllCards(context: Context)
    fun getToDos(id: Int)
}