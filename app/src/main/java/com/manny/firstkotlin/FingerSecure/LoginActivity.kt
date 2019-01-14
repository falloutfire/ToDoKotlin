package com.manny.securekeep

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.EditText
import android.widget.Toast
import com.manny.firstkotlin.CardActivity
import com.manny.firstkotlin.FingerSecure.CryptoUtils
import com.manny.firstkotlin.FingerSecure.FingerprintUtils
import com.manny.firstkotlin.R

class LoginActivity : AppCompatActivity() {
    private var mEditText: EditText? = null
    private var mPreferences: SharedPreferences? = null
    private var mFingerprintHelper: FingerprintHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Вход"
        setContentView(R.layout.activity_login)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mEditText = findViewById(R.id.editText) as EditText?

        findViewById(R.id.buttonLogin).setOnClickListener { prepareLogin() }

    }

    override fun onResume() {
        super.onResume()
        if (mPreferences!!.contains(PIN)) {
            prepareSensor()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mFingerprintHelper != null) {
            mFingerprintHelper!!.cancel()
        }
    }

    private fun prepareLogin() {

        val pin = mEditText!!.text.toString()
        if (pin.isNotEmpty()) {
            if (checkPin(pin)) {
                startActivity(Intent(this, CardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Пароль неверный", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Поле пароля не может быть пустым", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPin(pin: String): Boolean {
        return if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            mPreferences!!.getString(getString(R.string.pin), null) == pin
        } else {
            false
        }
    }

    private fun prepareSensor() {
        if (FingerprintUtils.isSensorStateAt(FingerprintUtils.mSensorState.READY, this)) {
            val cryptoObject = CryptoUtils.cryptoObject
            if (cryptoObject != null) {
                //Toast.makeText(this, "use fingerprint to login", Toast.LENGTH_LONG).show()
                mFingerprintHelper = FingerprintHelper(this)
                mFingerprintHelper!!.startAuth(cryptoObject)
            } else {
                mPreferences!!.edit().remove(PIN).apply()
                Toast.makeText(this, "new fingerprint enrolled. enter pin again", Toast.LENGTH_SHORT).show()
            }

        }
    }


    inner class FingerprintHelper internal constructor(private val mContext: Context) :
            FingerprintManagerCompat.AuthenticationCallback() {
        private var mCancellationSignal: CancellationSignal? = null

        internal fun startAuth(cryptoObject: FingerprintManagerCompat.CryptoObject) {
            mCancellationSignal = CancellationSignal()
            val manager = FingerprintManagerCompat.from(mContext)
            manager.authenticate(cryptoObject, 0, mCancellationSignal, this, null)
        }

        internal fun cancel() {
            if (mCancellationSignal != null) {
                mCancellationSignal!!.cancel()
            }
        }

        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
            Toast.makeText(mContext, errString, Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
            Toast.makeText(mContext, helpString, Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
            val cipher = result!!.cryptoObject.cipher
            val encoded = mPreferences!!.getString(PIN, null)
            val decoded = CryptoUtils.decode(encoded, cipher)
            mEditText!!.setText(decoded)
            Toast.makeText(mContext, "Пароль восстановлен", Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationFailed() {
            Toast.makeText(mContext, "Повторите снова", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {

        private val PIN = "pin"
    }
}
