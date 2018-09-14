package com.manny.firstkotlin

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

interface CardModelInterface {
    fun add(context: Context, card: Card)
    fun addToDo(context: Context, id: Long, toDo: ToDo)
    fun delete(context: Context, id: Long)
    fun getAll(context: Context): Cursor
    fun getToDo(context: Context, id: Long): Cursor
    fun setToDo(context: Context, idCard: Long, idTodo: Int, complete: Int)
    fun closeConnection(sqlIteHelper: CardSQLIteHelper, db: SQLiteDatabase)
    fun deleteToDo(context: Context, idTodo: Long, idCard: Long)
    fun saveToDo(context: Context, toDo: ToDo)
}