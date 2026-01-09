package com.nomakey.authenticator.core.otp

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

/**
 * HOTP Generator (RFC 4226)
 * HMAC-based One-Time Password Algorithm
 */
object HotpGenerator {

    /**
     * Generate HOTP code
     * @param secret Base32 encoded secret key
     * @param counter The counter value
     * @param digits Number of digits in the code (typically 6 or 8)
     * @param algorithm HMAC algorithm (HmacSHA1, HmacSHA256, HmacSHA512)
     * @return The HOTP code as a string
     */
    fun generate(
        secret: ByteArray,
        counter: Long,
        digits: Int = 6,
        algorithm: String = "HmacSHA1"
    ): String {
        // Convert counter to byte array (8 bytes, big-endian)
        val counterBytes = ByteArray(8)
        var value = counter
        for (i in 7 downTo 0) {
            counterBytes[i] = (value and 0xff).toByte()
            value = value shr 8
        }

        // Calculate HMAC
        val mac = Mac.getInstance(algorithm)
        val keySpec = SecretKeySpec(secret, algorithm)
        mac.init(keySpec)
        val hash = mac.doFinal(counterBytes)

        // Dynamic truncation (RFC 4226 Section 5.3)
        val offset = (hash[hash.size - 1] and 0x0f).toInt()
        val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                    ((hash[offset + 1].toInt() and 0xff) shl 16) or
                    ((hash[offset + 2].toInt() and 0xff) shl 8) or
                    (hash[offset + 3].toInt() and 0xff)

        // Generate code
        val otp = binary % powerOf10(digits)
        return otp.toString().padStart(digits, '0')
    }

    private fun powerOf10(digits: Int): Int {
        var result = 1
        repeat(digits) {
            result *= 10
        }
        return result
    }
}

