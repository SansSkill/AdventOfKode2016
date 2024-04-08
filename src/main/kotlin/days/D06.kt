package days

@Suppress("unused")
object D06 : Day {
    private val input = readFile()

    override suspend fun part1(): String {
        val arrs = Array(input[0].length) { IntArray(26) }
        for (s in input) for (i in s.indices) arrs[i][s[i] - 'a']++
        return arrs.map { arr -> arr.indices.maxBy(arr::get).plus('a'.code).toChar() }.joinToString("")
    }

    override suspend fun part2(): String {
        val arrs = Array(input[0].length) { IntArray(26) }
        for (s in input) for (i in s.indices) arrs[i][s[i] - 'a']++
        return arrs.map { arr -> arr.indices.minBy(arr::get).plus('a'.code).toChar() }.joinToString("")
    }
}
