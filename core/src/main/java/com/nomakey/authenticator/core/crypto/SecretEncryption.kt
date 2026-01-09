package com.nomakey.authenticator.core.crypto

import com.nomakey.authenticator.core.model.Account
import com.nomakey.authenticator.core.model.Algorithm
import com.nomakey.authenticator.core.model.OtpType
import org.json.JSONArray
import org.json.JSONObject

/**
 * Handles encryption and storage of OTP secrets
 * Uses KeystoreManager for secure storage
 */
class SecretEncryption(private val keystoreManager: KeystoreManager) {

    /**
     * Save an account securely
     */
    fun saveAccount(account: Account) {
        val accounts = getAllAccounts().toMutableList()

        // Remove existing account with same ID if present
        accounts.removeAll { it.id == account.id }

        // Add new/updated account
        accounts.add(account)

        // Serialize and save
        val json = serializeAccounts(accounts)
        keystoreManager.putString(KEY_ACCOUNTS, json)
    }

    /**
     * Get all accounts
     */
    fun getAllAccounts(): List<Account> {
        val json = keystoreManager.getString(KEY_ACCOUNTS) ?: return emptyList()
        return deserializeAccounts(json)
    }

    /**
     * Get account by ID
     */
    fun getAccount(id: String): Account? {
        return getAllAccounts().find { it.id == id }
    }

    /**
     * Delete an account
     */
    fun deleteAccount(id: String) {
        val accounts = getAllAccounts().filter { it.id != id }
        val json = serializeAccounts(accounts)
        keystoreManager.putString(KEY_ACCOUNTS, json)
    }

    /**
     * Clear all accounts
     */
    fun clearAllAccounts() {
        keystoreManager.remove(KEY_ACCOUNTS)
    }

    /**
     * Serialize accounts to JSON
     */
    private fun serializeAccounts(accounts: List<Account>): String {
        val jsonArray = JSONArray()

        for (account in accounts) {
            val jsonObject = JSONObject().apply {
                put("id", account.id)
                put("issuer", account.issuer)
                put("accountName", account.accountName)
                put("secret", account.secret)
                put("otpType", account.otpType.name)
                put("digits", account.digits)
                put("period", account.period)
                put("counter", account.counter)
                put("algorithm", account.algorithm.name)
                put("createdAt", account.createdAt)
            }
            jsonArray.put(jsonObject)
        }

        return jsonArray.toString()
    }

    /**
     * Deserialize accounts from JSON
     */
    private fun deserializeAccounts(json: String): List<Account> {
        val accounts = mutableListOf<Account>()
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val account = Account(
                id = jsonObject.getString("id"),
                issuer = jsonObject.getString("issuer"),
                accountName = jsonObject.getString("accountName"),
                secret = jsonObject.getString("secret"),
                otpType = OtpType.valueOf(jsonObject.getString("otpType")),
                digits = jsonObject.getInt("digits"),
                period = jsonObject.getInt("period"),
                counter = jsonObject.getLong("counter"),
                algorithm = Algorithm.valueOf(jsonObject.getString("algorithm")),
                createdAt = jsonObject.getLong("createdAt")
            )

            accounts.add(account)
        }

        return accounts
    }

    companion object {
        private const val KEY_ACCOUNTS = "encrypted_accounts"
    }
}

