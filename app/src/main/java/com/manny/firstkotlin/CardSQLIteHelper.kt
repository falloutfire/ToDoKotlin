package com.manny.firstkotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CardSQLIteHelper(var context: Context?,
                       var name: String? = "cards",
                       var factory: SQLiteDatabase.CursorFactory? = null,
                       var version: Int = 1) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE CARDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT)")
        db!!.execSQL("CREATE TABLE TODO (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAMEWORK TEXT, COMPLETE INTEGER, CARDID INTEGER)")
        insertCard(db, "work test", arrayListOf(ToDo(1, "clear table", false)))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when(oldVersion){
            1 -> {
                db!!.execSQL("DROP TABLE CARDS")
                db!!.execSQL("DROP TABLE TODO")
                db!!.execSQL("CREATE TABLE CARDS (_id INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT)")
                db!!.execSQL("CREATE TABLE TODO (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAMEWORK TEXT, COMPLETE INTEGER, CARDID INTEGER)")
                insertCard(db, "work test", arrayListOf(ToDo(1, "clear table", false)))
            }
        }
    }

    fun insertCard(db: SQLiteDatabase?, titleCard: String, todos: ArrayList<ToDo>) {
        val content = ContentValues()
        content.put("TITLE", titleCard)
        val id = db!!.insert("CARDS", null, content)
        content.clear()
        for (i in todos) {
            content.put("NAMEWORK", i.nameWork)
            val a = when (i.complete) {
                true -> 1
                false -> 0
            }
            content.put("COMPLETE", a.toString())
            content.put("CARDID", id)
            db!!.insert("TODO", null, content)
        }

    }
}