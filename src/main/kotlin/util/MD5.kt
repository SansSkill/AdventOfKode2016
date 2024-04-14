package util

import java.math.BigInteger
import java.security.MessageDigest

@Suppress("unused")
object MD5 {
    private val md5MessageDigest = MessageDigest.getInstance("MD5")

    fun toMD5(input:String): String {
        val inputBytes = input.toByteArray()
        val digest = md5MessageDigest.digest(inputBytes)
        val bigInteger = BigInteger(1, digest)
        return bigInteger.toString(16).padStart(32, '0')
    }
}
