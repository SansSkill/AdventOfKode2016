package days

@Suppress("unused")
object D09 : Day {
    private val input = readFile()[0]

    private fun getDecompressedLength(s: String, recursive: Boolean): Long {
        var count = 0L
        var i = 0
        while (i < s.length) {
            if (s[i] != '(') { count++; i++ }
            else {
                val idx = s.indexOf(')', startIndex = i)
                val (compressedLength, times) = s.substring(i + 1, idx).split("x").map(String::toInt)
                val decompressedLength = if (recursive) getDecompressedLength(s.substring(idx + 1, idx + 1 + compressedLength), true) else compressedLength.toLong()
                i = idx + compressedLength + 1
                count += decompressedLength * times
            }
        }
        return count
    }

    override fun part1(): String = getDecompressedLength(input, false).toString()

    override fun part2(): String = getDecompressedLength(input, true).toString()
}
