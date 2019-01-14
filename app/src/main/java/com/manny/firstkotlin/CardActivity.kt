package com.manny.firstkotlin

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.Loader
import android.support.v4.widget.SimpleCursorAdapter
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_todo.*

class CardActivity : AppCompatActivity(), CardViewInterface, LoaderCallbacks<Cursor> {

    var cardPresenter: CardPresenter = CardPresenter(this)
    lateinit var cursorAdapter: SimpleCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        setSupportActionBar(toolbar)

        cursorAdapter = SimpleCursorAdapter(this, R.layout.item_card/*R.layout.card_view*/, null, arrayOf("TITLE"), intArrayOf(R.id.card_name)/*intArrayOf(R.id.titleToDoCard)*/, 0)
        list_cards.adapter = cursorAdapter
        supportLoaderManager.initLoader(0, null, this)

        setListItemListener()
        registerForContextMenu(list_cards)

        btnRefresh.setOnClickListener {
            cardPresenter.getAllCards(this)
        }

        if (intent != null) {
            updateCards()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_create -> {
                val intent = Intent(this@CardActivity, AddCardActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun updateCards() {
        supportLoaderManager.getLoader<Any>(0)!!.forceLoad()
        setListItemListener()
    }

    private fun setListItemListener() {
        val listCard = findViewById(R.id.list_cards) as ListView
        listCard.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@CardActivity, DetailCardActivity::class.java)
            intent.putExtra(DetailCardActivity().EXTRA_ID_CARD, id)
            startActivity(intent)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.delete) {
            val acmi = item.menuInfo as AdapterView.AdapterContextMenuInfo
            cardPresenter.delete(this@CardActivity, acmi.id)
            updateCards()
            return true
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CardCursorLoader(this)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        cursorAdapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //cursorAdapter.swapCursor(null)

    }

    override fun getToDoOfCard(card: Card) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
