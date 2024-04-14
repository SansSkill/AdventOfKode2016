package days

import java.util.LinkedList
import util.MD5

@Suppress("unused")
object D14 : Day {
    private val salt: String = readFile()[0]

    override fun part1(): String = findIndexOf64thHash(0).toString()

    override fun part2(): String = findIndexOf64thHash(2016).toString()

    private fun findIndexOf64thHash(extraHashes: Int): Int {
        var nextPrefix = 0
        val hashes = LinkedList<String>()
        fun addHash() {
            var newHash = MD5.toMD5("$salt${nextPrefix++}")
            repeat(extraHashes) { newHash = MD5.toMD5(newHash) }
            hashes.add(newHash)
        }
        repeat(1001) { addHash() }

        var n = 0
        while (n < 64) {
            val hash = hashes.poll()
            require(hashes.size == 1000)
            getTriplet(hash)?.let { c ->
                if (hashes.any { futureHash -> "$c$c$c$c$c" in futureHash }) n++
            }
            addHash()
        }
        return nextPrefix.minus(1002)
    }

    private fun getTriplet(s: String): Char? =
        (0.. s.length - 3).firstOrNull { i -> s[i] == s[i + 1] && s[i] == s[i + 2] }?.let(s::get)
}
