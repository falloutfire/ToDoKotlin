package com.manny.firstkotlin

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

interface CardModelInterface {
    fun add(context: Context, card: Card)
    fun addToDo(context: Context, id: Long, toDo: ToDo)
    fun save(context: Context, id: Long, card: Card)
    fun delete(context: Context, card: Card)
    fun getAll(context: Context): Cursor
    fun getToDo(context: Context, id: Long): Cursor
    fun setToDo(context: Context, idCard: Long, idTodo: Int, complete: Int)
    fun closeConnection(sqlIteHelper: CardSQLIteHelper, db: SQLiteDatabase)
}