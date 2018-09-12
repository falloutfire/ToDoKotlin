package com.manny.firstkotlin

import android.content.Context
import android.database.Cursor
import android.support.v4.content.CursorLoader

class CardCursorLoader(context: Context) : CursorLoader(context) {

    var cardPresenter: CardPresenter = CardPresenter()

    override fun loadInBackground(): Cursor? {
        return cardPresenter.getCardsCursor(context)
    }
}

class ToDoCursorLoader(context: Context, id: Long) : CursorLoader(context) {
    var cardPresenter: CardPresenter = CardPresenter()
    var idCard = id

    override fun loadInBackground(): Cursor? {
        return cardPresenter.getToDoCursor(context, idCard)
    }
}