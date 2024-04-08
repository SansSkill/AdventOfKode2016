package days

@Suppress("unused")
object D07 : Day {
    private val input = readFile().map { s -> s.split("[", "]") }

    override suspend fun part1(): String {
        fun isABBA(s: String): Boolean =
            (0..s.length - 4).any { i -> s[i] != s[i + 1] && s[i + 1] == s[i + 2] && s[i] == s[i + 3] }
        return input.count { splits ->
            val abbaOutside = (splits.indices step 2).any { i -> isABBA(splits[i]) }
            val abbaInside = (1..<splits.size step 2).none { i -> isABBA(splits[i]) }
            abbaInside && !abbaOutside
        }.toString()
    }

    override suspend fun part2(): String {
        fun getABAs(s: String): Set<Pair<Char, Char>> =
            buildSet {
                (0 .. s.length - 3)
                    .asSequence()
                    .filter { i -> s[i] != s[i + 1] && s[i] == s[i + 2] }
                    .forEach { add(s[it] to s[it + 1]) }
            }

        return input.count { splits ->
            val abasInside = (splits.indices step 2).map { i -> getABAs(splits[i]) }.reduce(Set<Pair<Char, Char>>::plus)
            val abasOutside = (1..<splits.size step 2).map { i -> getABAs(splits[i]) }.reduce(Set<Pair<Char, Char>>::plus)
            abasInside.any { (a, b) -> b to a in abasOutside }
        }.toString()
    }
}
