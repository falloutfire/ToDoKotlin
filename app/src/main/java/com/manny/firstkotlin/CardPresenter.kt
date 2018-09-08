package com.manny.firstkotlin

import android.content.Context
import android.database.Cursor

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
        cardModelInterface.add(context, card)
    }

    override fun addToDo(context: Context, id: Long, toDo: ToDo) {
        cardModelInterface.addToDo(context, id, toDo)
        toDoViewInterface.setUpToDos()
    }

    override fun save(context: Context, id: Long, card: Card) {
        cardModelInterface.save(context, id, card)
    }

    override fun delete(context: Context, id: Long) {
        cardModelInterface.delete(context, id)
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

    fun setToDoCursor(context: Context, idCard: Long, idTodo: Int, complete: Int){
        cardModelInterface.setToDo(context, idCard, idTodo, complete)
        getToDoCursor(context, idCard)
    }
}