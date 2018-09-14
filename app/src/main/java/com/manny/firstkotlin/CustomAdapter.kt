package com.manny.firstkotlin

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.support.v4.widget.SimpleCursorAdapter
import android.text.Editable
import android.text.TextWatcher
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
        view.textNamework.setText(cursor.getString(cursor.getColumnIndex("NAMEWORK")))

        view.textNamework.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val toDo = ToDo(id.toInt(), s.toString(), when (cursor.getInt(cursor.getColumnIndex("COMPLETE"))) {
                    1 -> true
                    else -> false
                })

                cardPresenter.saveToDo(mContext, toDo)
            }
        })
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

        view.textNamework.setText(cursor.getString(cursor.getColumnIndex("NAMEWORK")))

    }

}
