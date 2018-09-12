package com.manny.firstkotlin

import android.content.Context
import android.database.Cursor
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class CardPresenter() : CardPresenterInterface {

    private lateinit var cardViewInterface: CardViewInterface
    private lateinit var toDoViewInterface: ToDoViewInterface
    private var cardModelInterface: CardModelInterface = CardModel()

    constructor(cardView: CardViewInterface) : this() {
        cardViewInterface = cardView
    }

    constructor(toDoView: ToDoViewInterface) : this() {
        toDoViewInterface = toDoView
    }

    override fun addCard(context: Context, card: Card) {
        launch(UI) {
            withContext(CommonPool) { cardModelInterface.add(context, card) }
        }
    }

    override fun addToDo(context: Context, id: Long, toDo: ToDo) {
        launch(UI) {
            withContext(CommonPool) { cardModelInterface.addToDo(context, id, toDo) }
        }
        toDoViewInterface.setUpToDos()
    }

    override fun delete(context: Context, id: Long) {
        launch(UI) {
            withContext(CommonPool) {
                cardModelInterface.delete(context, id)
            }
        }
        cardViewInterface.updateCards()
    }

    override fun getAllCards(context: Context) {
        cardViewInterface.updateCards()
    }

    fun getCardsCursor(context: Context): Cursor {
        return cardModelInterface.getAll(context)
    }

    fun getToDoCursor(context: Context, id: Long): Cursor {
        return cardModelInterface.getToDo(context, id)
    }

    fun setToDoCursor(context: Context, idCard: Long, idTodo: Int, complete: Int) {
        launch(UI) {
            withContext(CommonPool) {
                cardModelInterface.setToDo(context, idCard, idTodo, complete)
            }
        }
        getToDoCursor(context, idCard)
    }

    fun deleteToDo(context: Context, idTodo: Long, idCard: Long) {
        cardModelInterface.deleteToDo(context, idTodo, idCard)
    }
}