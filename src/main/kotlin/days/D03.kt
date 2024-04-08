package days

@Suppress("unused")
object D03 : Day {
    private val input: List<List<Int>>

    init {
        val regex = Regex("\\s*(\\d+)\\s*(\\d+)\\s*(\\d+)\\s*")
        input = readFile().map { line ->
            regex.matchEntire(line)?.groupValues?.drop(1)?.map(String::toInt)
                ?: error("Failed to three numbers in line '$line'")
        }
    }

    override suspend fun part1(): String = input.count(::isTriangle).toString()

    override suspend fun part2(): String = (input.indices step 3).sumOf { y ->
        (0..2).count { x -> isTriangle(input[y][x], input[y + 1][x], input[y + 2][x]) }
    }.toString()

    private fun isTriangle(ns: List<Int>): Boolean = ns.sum() > 2 * ns.max()
    private fun isTriangle(x: Int, y: Int, z: Int): Boolean = isTriangle(listOf(x, y, z))
}