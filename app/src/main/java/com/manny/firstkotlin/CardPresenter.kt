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

    override fun addCard(context: Context) {
        cardModelInterface.add(context, Card("1", "workharder and faster", arrayListOf(ToDo(1, "Do better", false), ToDo(2, "Do stronger", true))))
        cardViewInterface.updateCards()
    }

    override fun addToDo(context: Context, id: Long, toDo: ToDo) {
        cardModelInterface.addToDo(context, id, toDo)
        toDoViewInterface.setUpToDos()
    }

    override fun save(context: Context, id: Long, card: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        cardModelInterface.save(context, id, card)

    }

    override fun delete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllCards(context: Context) {
        /*val cardsCursor = cardModelInterface.getAll(context)*/
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

    override fun getToDos(id: Int) {
        //toDoViewInterface.setUpToDos(cardModelInterface.getAll()[id].toDos)
    }
}