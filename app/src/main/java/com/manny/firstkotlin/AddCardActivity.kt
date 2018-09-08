package com.manny.firstkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_card.*


class AddCardActivity : AppCompatActivity() {

    private val cardPresenter = CardPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        btnAddCard.setOnClickListener {
            createCard()
        }
    }

    private fun createCard() {
        if (titleCard.text.toString() != "") {
            val card = Card("1", titleCard.text.toString(), arrayListOf())
            cardPresenter.addCard(this, card)

            val intent = Intent(this@AddCardActivity, CardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            Toast.makeText(this, "Card complete created", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        } else {
            Toast.makeText(this, "You dont have title card", Toast.LENGTH_SHORT).show()
        }
    }


}
