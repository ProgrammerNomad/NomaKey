package com.nomakey.authenticator.core.crypto

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Manager for secure storage using EncryptedSharedPreferences
 * All secrets are encrypted with AES-256-GCM
 */
class KeystoreManager(private val context: Context) {

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val encryptedPrefs by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Store encrypted value
     */
    fun putString(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }

    /**
     * Retrieve encrypted value
     */
    fun getString(key: String, defaultValue: String? = null): String? {
        return encryptedPrefs.getString(key, defaultValue)
    }

    /**
     * Remove encrypted value
     */
    fun remove(key: String) {
        encryptedPrefs.edit().remove(key).apply()
    }

    /**
     * Clear all encrypted data
     */
    fun clear() {
        encryptedPrefs.edit().clear().apply()
    }

    /**
     * Check if key exists
     */
    fun contains(key: String): Boolean {
        return encryptedPrefs.contains(key)
    }

    companion object {
        private const val PREFS_NAME = "nomakey_secure_prefs"
    }
}


