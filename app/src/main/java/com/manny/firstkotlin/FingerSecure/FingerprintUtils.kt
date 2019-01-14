package com.manny.firstkotlin.FingerSecure

import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat

object FingerprintUtils {

    //Проверка наличия сенсора отпечатков
    fun checkFingerprintCompatibility(context: Context): Boolean {
        return FingerprintManagerCompat.from(context).isHardwareDetected
    }

    //Проверка сенсора
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkSensorState(context: Context): mSensorState {
        if (checkFingerprintCompatibility(context)) {

            val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            if (!keyguardManager.isKeyguardSecure) {
                return mSensorState.NOT_BLOCKED
            }

            return if (!FingerprintManagerCompat.from(context).hasEnrolledFingerprints()) {
                mSensorState.NO_FINGERPRINTS
            } else mSensorState.READY

        } else {
            return mSensorState.NOT_SUPPORTED
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun isSensorStateAt(state: mSensorState, context: Context): Boolean {
        return checkSensorState(context) == state
    }

    //Состояния сенсора
    enum class mSensorState {
        NOT_SUPPORTED,
        NOT_BLOCKED,
        NO_FINGERPRINTS,
        READY
    }
}
