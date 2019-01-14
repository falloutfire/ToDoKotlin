package com.manny.securekeep

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.Toast
import com.manny.firstkotlin.CardActivity
import com.manny.firstkotlin.FingerSecure.CryptoUtils
import com.manny.firstkotlin.R


class FirstStartActivity : AppCompatActivity() {

    private var mEditText: EditText? = null
    private var mPreferences: SharedPreferences? = null

    private val PIN = "pin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Первый вход"
        setContentView(R.layout.activity_first_start)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isAccessed = mPreferences!!.getBoolean(getString(R.string.is_accessed), false)
        if (!isAccessed) {
            val edit = mPreferences!!.edit()
            edit.putBoolean(getString(R.string.is_accessed), java.lang.Boolean.TRUE)
            edit.apply()

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        mEditText = findViewById(R.id.editText) as EditText

        findViewById(R.id.buttonStart).setOnClickListener { saveNew(mEditText!!.text.toString()) }
    }

    private fun saveNew(pin: String) {
        if (pin.isNotEmpty()) {
            savePin(pin)
            startActivity(Intent(this, CardActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Поле пароля не может быть пустым", Toast.LENGTH_SHORT).show()
        }
    }

    private fun savePin(pin: String) {
        val encoded = CryptoUtils.encode(pin)
        mPreferences!!.edit().putString(PIN, encoded).apply()
        mPreferences!!.edit().putString(getString(R.string.pin), pin).apply()
    }
}
