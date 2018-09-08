package com.manny.firstkotlin

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.Loader
import android.support.v4.widget.SimpleCursorAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.coroutines.experimental.delay
import java.util.concurrent.TimeUnit

class CardActivity : AppCompatActivity(), CardViewInterface, LoaderCallbacks<Cursor> {

    var cardPresenter: CardPresenter = CardPresenter(this)
    lateinit var cursorAdapter: SimpleCursorAdapter
    var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        setSupportActionBar(toolbar)

        cursorAdapter = SimpleCursorAdapter(this, R.layout.item_card, null, arrayOf("TITLE"), intArrayOf(R.id.card_name), 0)
        list_cards.adapter = cursorAdapter
        /*launch(UI) {
            setTextAfterDelay(10, "Hello from a coroutine!")
        }*/
        supportLoaderManager.initLoader(0, null, this)

        setListItemListener()

        btnRefresh.setOnClickListener {
            cardPresenter.getAllCards(this)
        }
    }

    private suspend fun setTextAfterDelay(seconds: Long, s: String) {
        delay(seconds, TimeUnit.SECONDS)
        Log.e(s, seconds.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_create -> {
                cardPresenter.addCard(this)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun addCard() {
        supportLoaderManager.getLoader<Any>(0)!!.forceLoad()
    }

    override fun updateCards() {
        supportLoaderManager.getLoader<Any>(0)!!.forceLoad()
        setListItemListener()
    }

    fun setListItemListener(){
        val listCard = findViewById<ListView>(R.id.list_cards)
        listCard.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@CardActivity, DetailCardActivity::class.java)
            intent.putExtra(DetailCardActivity().EXTRA_ID_CARD, id)
            startActivity(intent)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CardCursorLoader(this)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        cursorAdapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getToDoOfCard(card: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
