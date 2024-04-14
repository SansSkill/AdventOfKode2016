package days

@Suppress("unused")
object D16 : Day {
    private val initialState = readFile()[0]

    override fun part1(): String = getCheckSum(dragonCurve(272))

    override fun part2(): String = getCheckSum(dragonCurve(35651584))

    private fun getCheckSum(s: String): String {
        val checksum = buildString {
            for (i in s.indices step 2) if (s[i] == s[i + 1]) append('1') else append('0')
        }
        return if (checksum.length and 1 == 0) getCheckSum(checksum) else checksum
    }

    private fun dragonCurve(minimumLength: Int): String =
        buildString {
            append(initialState)
            while (length < minimumLength) {
                val currentLength = length
                append('0')
                for (i in currentLength - 1 downTo 0) if (this[i] == '0') append('1') else append('0')
            }
            delete(minimumLength, length)
        }
}
