package com.manny.firstkotlin

import android.content.Context
import android.database.Cursor
import android.support.v4.content.CursorLoader

class CardCursorLoader(context: Context) : CursorLoader(context) {

    var cardPresenter: CardPresenter = CardPresenter()

    override fun loadInBackground(): Cursor? {
        val cursor = cardPresenter.getCardsCursor(context)
        /*try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }*/
        return cursor
    }
}

class ToDoCursorLoader(context: Context, id: Long) : CursorLoader(context) {
    var cardPresenter: CardPresenter = CardPresenter()
    var idCard = id

    override fun loadInBackground(): Cursor? {
        val cursor = cardPresenter.getToDoCursor(context, idCard)
        /*try {
            TimeUnit.SECONDS.sleep(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }*/
        return cursor
    }
}