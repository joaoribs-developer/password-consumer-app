package com.kafka.consumer.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.Locale

@Service
class CryptoService(
    @Value("\${password.crypto}")
    private val secretKey: String
) {

    fun encrypt(value: String): String {
        val toHash = value + secretKey
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(toHash.toByteArray(Charsets.UTF_8))
        return digest.toHex()
    }

    /**
     * Simulation of a decryption process using a private key.
     */
    fun decrypt(value: String, expectedHexHash: String): Boolean {
        val computed = encrypt(value)
        return computed.equals(expectedHexHash, ignoreCase = true)
    }

    private fun ByteArray.toHex(): String {
        val sb = StringBuilder(this.size * 2)
        for (b in this) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString().lowercase(Locale.getDefault())
    }
}