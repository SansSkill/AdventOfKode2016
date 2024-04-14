package util

import java.math.BigInteger

object IntUtil {
    fun Int.lcm(other: Int): Int =
        this * other / BigInteger(this.toString()).gcd(BigInteger(other.toString())).toInt()
}
