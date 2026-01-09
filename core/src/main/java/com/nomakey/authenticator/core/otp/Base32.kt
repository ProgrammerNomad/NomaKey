package com.nomakey.authenticator.core.otp

import java.util.Locale

/**
 * Utility for Base32 encoding/decoding
 * Required for OTP secret handling
 */
object Base32 {

    private const val BASE32_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"

    /**
     * Decode Base32 encoded string to byte array
     * @param encoded Base32 encoded string
     * @return Decoded byte array
     */
    fun decode(encoded: String): ByteArray {
        val cleanInput = encoded.uppercase(Locale.ROOT)
            .replace(" ", "")
            .replace("-", "")
            .trimEnd('=')

        if (cleanInput.isEmpty()) {
            return ByteArray(0)
        }

        val outputLength = cleanInput.length * 5 / 8
        val output = ByteArray(outputLength)

        var buffer = 0
        var bitsLeft = 0
        var outputIndex = 0

        for (char in cleanInput) {
            val value = BASE32_CHARS.indexOf(char)
            if (value == -1) {
                throw IllegalArgumentException("Invalid Base32 character: $char")
            }

            buffer = (buffer shl 5) or value
            bitsLeft += 5

            if (bitsLeft >= 8) {
                output[outputIndex++] = (buffer shr (bitsLeft - 8)).toByte()
                bitsLeft -= 8
            }
        }

        return output
    }

    /**
     * Encode byte array to Base32 string
     * @param input Byte array to encode
     * @return Base32 encoded string
     */
    fun encode(input: ByteArray): String {
        if (input.isEmpty()) {
            return ""
        }

        val outputLength = ((input.size * 8 + 4) / 5)
        val output = StringBuilder(outputLength)

        var buffer = 0
        var bitsLeft = 0

        for (byte in input) {
            buffer = (buffer shl 8) or (byte.toInt() and 0xff)
            bitsLeft += 8

            while (bitsLeft >= 5) {
                val index = (buffer shr (bitsLeft - 5)) and 0x1f
                output.append(BASE32_CHARS[index])
                bitsLeft -= 5
            }
        }

        if (bitsLeft > 0) {
            val index = (buffer shl (5 - bitsLeft)) and 0x1f
            output.append(BASE32_CHARS[index])
        }

        return output.toString()
    }
}

