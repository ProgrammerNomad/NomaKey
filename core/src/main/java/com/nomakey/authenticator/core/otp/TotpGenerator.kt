package com.nomakey.authenticator.core.otp

/**
 * TOTP Generator (RFC 6238)
 * Time-based One-Time Password Algorithm
 *
 * TOTP is an extension of HOTP where the counter is derived from the current time
 */
object TotpGenerator {

    /**
     * Generate TOTP code
     * @param secret Base32 encoded secret key
     * @param currentTimeMillis Current time in milliseconds
     * @param period Time step period in seconds (typically 30)
     * @param digits Number of digits in the code (typically 6 or 8)
     * @param algorithm HMAC algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
     * @return The TOTP code as a string
     */
    fun generate(
        secret: ByteArray,
        currentTimeMillis: Long = System.currentTimeMillis(),
        period: Int = 30,
        digits: Int = 6,
        algorithm: String = "HmacSHA1"
    ): String {
        // Calculate time counter (T = (Current Unix time - T0) / X)
        // T0 = 0 (Unix epoch), X = time step (period)
        val counter = currentTimeMillis / 1000 / period

        // TOTP is just HOTP with time-based counter
        return HotpGenerator.generate(secret, counter, digits, algorithm)
    }

    /**
     * Calculate remaining seconds until next code generation
     * @param currentTimeMillis Current time in milliseconds
     * @param period Time step period in seconds
     * @return Remaining seconds
     */
    fun getRemainingSeconds(
        currentTimeMillis: Long = System.currentTimeMillis(),
        period: Int = 30
    ): Int {
        val elapsedInPeriod = (currentTimeMillis / 1000) % period
        return (period - elapsedInPeriod).toInt()
    }
}

