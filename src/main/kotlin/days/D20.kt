package days

@Suppress("unused")
object D20 : Day {
    private val ranges: List<LongRange>

    init {
        val unmergedRanges = readFile().map { s -> s.split("-").let { (ls, rs) -> ls.toLong()..rs.toLong() } }.sortedWith(compareBy(LongRange::first).thenBy(LongRange::last))
        ranges = buildList {
            add(unmergedRanges[0])
            for (i in 1 ..< unmergedRanges.size) {
                val nextRange = unmergedRanges[i]
                val prevRange = get(lastIndex)
                if (nextRange.first > prevRange.last + 1) add(nextRange)
                else {
                    removeLast()
                    add(prevRange.first..prevRange.last.coerceAtLeast(nextRange.last))
                }
            }
        }
    }

    override fun part1(): String = ranges[0].last.inc().toString()

    override fun part2(): String = 4294967295L.inc().minus(ranges.sumOf { range -> range.last - range.first + 1 }).toString()
}
