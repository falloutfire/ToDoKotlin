package com.manny.firstkotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

data class Card(var id: String, var title: String, var toDos: ArrayList<ToDo>)

data class ToDo(var id: Int, var nameWork: String, var complete: Boolean)

class CardModel : CardModelInterface {

    override fun save(context: Context, id: Long, card: Card) {
        val db = CardSQLIteHelper(context).writableDatabase
    }

    override fun add(context: Context, card: Card) {
        val db = CardSQLIteHelper(context).writableDatabase
        val content = ContentValues()
        content.put("TITLE", card.title)
        val id = db!!.insert("CARDS", null, content)
        content.clear()
        for (i in card.toDos) {
            content.put("NAMEWORK", i.nameWork)
            val a = when (i.complete) {
                true -> 1
                false -> 0
            }
            content.put("COMPLETE", a.toString())
            content.put("CARDID", id)
            db!!.insert("TODO", null, content)
        }
        db.close()
    }

    override fun delete(context: Context, card: Card) {
        //cards.remove(card)
    }

    @SuppressLint("Recycle")
    override fun getAll(context: Context): Cursor {
        val cardSQLIteHelper = CardSQLIteHelper(context)
        var cursor: Cursor? = null
        return try {
            val db = cardSQLIteHelper.readableDatabase
            cursor = db.query("CARDS", arrayOf("_id", "TITLE"), null, null, null, null, null)
            cursor
        } catch (e: SQLiteException) {
            cursor!!
        }
    }

    override fun getToDo(context: Context, id: Long): Cursor {
        val cardSQLIteHelper = CardSQLIteHelper(context)
        var cursor: Cursor? = null
        return try {
            val db = cardSQLIteHelper.readableDatabase
            cursor = db.query("TODO", arrayOf("_id", "NAMEWORK", "COMPLETE", "CARDID"), "CARDID = $id", null, null, null, null)
            cursor
        } catch (e: SQLiteException) {
            cursor!!
        }
    }

    override fun setToDo(context: Context, idCard: Long, idTodo: Int, complete: Int) {
        val cardSQLIteHelper = CardSQLIteHelper(context)
        val content = ContentValues()
        content.put("CARDID", idCard)
        content.put("COMPLETE", complete)
        val db = cardSQLIteHelper.writableDatabase
        db.update("TODO", content, "_id = $idTodo", null)
        closeConnection(cardSQLIteHelper, db)
    }

    override fun addToDo(context: Context, id: Long, toDo: ToDo) {
        val contentValues = ContentValues()
        val cardSQLIteHelper = CardSQLIteHelper(context)
        contentValues.put("NAMEWORK", toDo.nameWork)
        contentValues.put("COMPLETE", when (toDo.complete) {
            true -> 1
            false -> 0
        })
        contentValues.put("CARDID", id)
        val db = cardSQLIteHelper.writableDatabase
        db.insert("TODO", null, contentValues)
        closeConnection(cardSQLIteHelper, db)
    }

    override fun closeConnection(sqlIteHelper: CardSQLIteHelper, db: SQLiteDatabase) {
        db.close()
        sqlIteHelper.close()
    }

}
