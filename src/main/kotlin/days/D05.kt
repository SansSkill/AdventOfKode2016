package days

import java.math.BigInteger
import java.security.MessageDigest

@Suppress("unused")
object D05 : Day {
    private val md5 = MessageDigest.getInstance("MD5")
    private val prefix = readFile()[0]

    override suspend fun part1(): String =
        buildString {
            var n = 0
            while (length < 8) {
                val hash = md5("$prefix${n++}")
                if (hash.startsWith("00000")) append(hash[5])
            }
        }

    override suspend fun part2(): String {
        val charArray = CharArray(8).apply { fill('.') }
        var n = 0
        while (charArray.any { c -> c == '.' }) {
            val hash = md5("$prefix${n++}")
            if (hash.startsWith("00000") && hash[5] in '0'..'7') {
                val i = hash[5] - '0'
                if (charArray[i] == '.') charArray[i] = hash[6]
            }
        }
        return charArray.joinToString("")
    }

    private fun md5(input:String): String = BigInteger(1, md5.digest(input.toByteArray())).toString(16).padStart(32, '0')
}
