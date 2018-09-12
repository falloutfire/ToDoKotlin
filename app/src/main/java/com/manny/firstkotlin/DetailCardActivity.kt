package com.manny.firstkotlin

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_detail_card.*

class DetailCardActivity : AppCompatActivity(), ToDoViewInterface, LoaderManager.LoaderCallbacks<Cursor> {

    var EXTRA_ID_CARD: String = "cardId"
    private var cardPresenter = CardPresenter(this)
    lateinit var cursorAdapter: CustomAdapter
    var idCard: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_card)
        idCard = intent.extras.getLong(EXTRA_ID_CARD)
        cardPresenter.getToDoCursor(this@DetailCardActivity, idCard)
        setSupportActionBar(toolbarTodo)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        cursorAdapter = CustomAdapter(this, R.layout.item_todos, null, arrayOf("NAMEWORK", "COMPLETE"), intArrayOf(R.id.textNamework, R.id.checkboxTodo), 0, idCard)
        list_todos.adapter = cursorAdapter
        registerForContextMenu(list_todos)
        supportLoaderManager.initLoader(0, null, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("InflateParams")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_create -> {
                //if (list_layout_todo.childCount < 3) {
                    val child = layoutInflater.inflate(R.layout.item_adding_todo, null)
                    list_layout_todo.addView(child)
                    val btnAdd = child.findViewById<Button>(R.id.close_editText)
                    btnAdd.setOnClickListener {
                        val editNamework = child.findViewById<EditText>(R.id.editTextNamework)
                        val todo = ToDo(0, editNamework.text.toString(), false)
                        cardPresenter.addToDo(this@DetailCardActivity, idCard, todo)
                        if (editNamework.isFocusable) {
                            editNamework.isFocusable = false
                        }
                        supportLoaderManager.getLoader<Any>(0)!!.forceLoad()
                        list_layout_todo.removeView(child)
                        //}
                }
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu!!.add(0, 1, 0, "Delete")
    }

    override fun onContextItemSelected(p0: MenuItem?): Boolean {
        if (p0!!.itemId == 1) {
            val acmi = p0.menuInfo as AdapterView.AdapterContextMenuInfo
            cardPresenter.deleteToDo(this@DetailCardActivity, acmi.id, idCard)
            setUpToDos()
            return true
        }
        return super.onContextItemSelected(p0)
    }

    override fun setUpToDos() {
        supportLoaderManager.getLoader<Any>(0)!!.forceLoad()
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return ToDoCursorLoader(this, idCard)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        cursorAdapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
