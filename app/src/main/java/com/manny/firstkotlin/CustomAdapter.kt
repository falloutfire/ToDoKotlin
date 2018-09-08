package com.manny.firstkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.support.v4.widget.SimpleCursorAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import kotlinx.android.synthetic.main.item_todos.view.*


class CustomAdapter(mContext: Context, layout: Int, c: Cursor?, from: Array<String>, to: IntArray, flags: Int, val idCard: Long) : SimpleCursorAdapter(mContext, layout, c, from, to, flags), View.OnClickListener {

    private var cardPresenter = CardPresenter()

    override fun onClick(v: View?) {
        val checkBox = v as CheckBox
        val id = checkBox.tag.toString().toInt()
        val complete = when (checkBox.isChecked) {
            true -> 1
            false -> 0
        }
        cardPresenter.setToDoCursor(mContext, idCard, id, complete)
    }

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    @SuppressLint("InflateParams")
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val view = mLayoutInflater.inflate(R.layout.item_todos, null)
        val id = cursor!!.getString(cursor.getColumnIndex("_id"))
        val checkBox = view.findViewById<CheckBox>(R.id.checkboxTodo)
        checkBox.tag = id
        checkBox.isChecked = when (cursor.getInt(cursor.getColumnIndex("COMPLETE"))) {
            1 -> true
            else -> false
        }
        checkBox.setOnClickListener(this)
        view.textNamework.text = cursor.getString(cursor.getColumnIndex("NAMEWORK"))
        return view
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val checkBox = view!!.findViewById<CheckBox>(R.id.checkboxTodo)
        val id = cursor!!.getString(cursor.getColumnIndex("_id"))
        checkBox.tag = id
        checkBox.isChecked = when (cursor.getInt(cursor.getColumnIndex("COMPLETE"))) {
            1 -> true
            else -> false
        }

        view.textNamework.text = cursor.getString(cursor.getColumnIndex("NAMEWORK"))

        /*val count = to.size
        val from = from
        val to = mTo

        for (i in 0 until count) {
            val v = view.findViewById<View>(to[i])
            if (v != null) {
                *//*var bound = false
                if (binder != null) {
                    bound = binder.setViewValue(v, cursor, from[i])
                }*//*

                //if (!bound) {
                var text: String? = cursor.getString("$from[i]")
                if (text == null) {
                    text = ""
                }

                when (v) {
                    is TextView -> setViewText(v, text)
                    is ImageView -> setViewImage(v, text)
                    is CheckBox -> setViewCheck(v, text)
                    else -> throw IllegalStateException(v.javaClass.name + " is not a "
                            + " view that can be bounds by this SimpleCursorAdapter")
                }
                //}
            }
        }*/
    }

    /*private fun setViewCheck(v: CheckBox, text: String) {
        when (text) {
            "1" -> v.isChecked = true
            else -> v.isChecked = false
        }
    }*/
}
/*
class Adapter(private val mContext: Context, layout: Int, c: Cursor, from: Array<String>, to: IntArray) : SimpleCursorAdapter(mContext, layout, c, from, to), View.OnClickListener {

    private val mWidgetState = ArrayList<String>()
    private val mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = LayoutInflater.from(mContext)
    }

    override fun newView(context: Context?, c: Cursor?, parent: ViewGroup): View {
        val view = mLayoutInflater.inflate(R.layout.listbox, null)
        val id = c!!.getString(c.getColumnIndex("_id"))
        val checkBox = view.findViewById(R.id.checkBox) as CheckBox
        checkBox.isChecked = true
        checkBox.tag = id
        checkBox.setOnClickListener(this)
        return view
    }

    fun bindView(view: View, context: Context, c: Cursor) {
        val checkBox = view.findViewById(R.id.checkBox) as CheckBox
        val idIndex = c.getColumnIndex("_id")
        val id = c.getString(idIndex)
        checkBox.isChecked = !mWidgetState.contains(id)
        checkBox.tag = id
    }

    fun onClick(v: View) {
        val values = ContentValues()
        val checkBox = v as CheckBox
        val id = checkBox.tag.toString()
        val uri = ContentUris.withAppendedId(AppProvider.CONTENT_URI, java.lang.Long.parseLong(id))
        val check = !mWidgetState.contains(id)
        if (check) {
            checkBox.isChecked = false
            mWidgetState.addCard(id)
            values.put("active", 0)
            mContext.getContentResolver().update(uri, values, "_id=?", arrayOf(id))
        } else {
            checkBox.isChecked = true
            mWidgetState.remove(id)
            values.put("active", 1)
            mContext.getContentResolver().update(uri, values, "_id=?", arrayOf(id))
        }
    }

    companion object {

        private val TAG = Adapter::class.java.simpleName
    }


}*/
