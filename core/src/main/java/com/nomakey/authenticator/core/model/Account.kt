package com.nomakey.authenticator.core.model

/**
 * Represents an OTP account/entry
 */
data class Account(
    val id: String,
    val issuer: String,
    val accountName: String,
    val secret: String, // Base32 encoded secret
    val otpType: OtpType,
    val digits: Int = 6,
    val period: Int = 30, // For TOTP
    val counter: Long = 0, // For HOTP
    val algorithm: Algorithm = Algorithm.SHA1,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Type of OTP
 */
enum class OtpType {
    TOTP, // Time-based OTP (RFC 6238)
    HOTP  // HMAC-based OTP (RFC 4226)
}

/**
 * Hash algorithm for OTP generation
 */
enum class Algorithm {
    SHA1,
    SHA256,
    SHA512;

    fun getHmacAlgorithm(): String = when(this) {
        SHA1 -> "HmacSHA1"
        SHA256 -> "HmacSHA256"
        SHA512 -> "HmacSHA512"
    }
}


