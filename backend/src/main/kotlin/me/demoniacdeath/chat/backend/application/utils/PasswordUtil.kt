package me.demoniacdeath.chat.backend.application.utils

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import java.util.Arrays
import java.util.Base64
import java.util.Random
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordUtil {

    private val RANDOM = SecureRandom()
    private val ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    private val ITERATIONS = 10000
    private val KEY_LENGTH = 256

    fun getSalt(length: Int): String {
        val returnValue = StringBuilder(length)

        for (i in 0 until length) {
            returnValue.append(ALPHABET[RANDOM.nextInt(ALPHABET.length)])
        }

        return String(returnValue)
    }

    fun hash(password: CharArray, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        Arrays.fill(password, Character.MIN_VALUE)
        try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return skf.generateSecret(spec).encoded
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }

    fun generateSecurePassword(password: String, salt: String): String? {
        val returnValue: String?

        val securePassword = hash(password.toCharArray(), salt.toByteArray())

        returnValue = Base64.getEncoder().encodeToString(securePassword)

        return returnValue
    }

    fun verifyUserPassword(providedPassword: String,
                           securedPassword: String, salt: String): Boolean {
        val returnValue: Boolean

        // Generate New secure password with the same salt
        val newSecurePassword = generateSecurePassword(providedPassword, salt)

        // Check if two passwords are equal
        returnValue = newSecurePassword!!.equals(securedPassword, ignoreCase = true)

        return returnValue
    }
}