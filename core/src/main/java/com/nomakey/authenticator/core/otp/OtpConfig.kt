package com.nomakey.authenticator.core.otp

/**
 * Configuration for OTP generation
 */
data class OtpConfig(
    val digits: Int = 6,
    val period: Int = 30,
    val algorithm: String = "HmacSHA1"
)

