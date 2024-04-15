package days

@Suppress("unused")
object D18 : Day {
    private val initialState: BooleanArray = readFile()[0].map { c -> c == '^' }.toBooleanArray()

    override fun part1(): String = getSafeTiles(40).toString()

    override fun part2(): String = getSafeTiles(400000 ).toString()

    private fun getSafeTiles(rows: Int): Int {
        var row = initialState
        var count = row.count { b -> !b }
        repeat(rows - 1) {
            row = generateNextRow(row)
            count += row.count { b -> !b }
        }
        return count
    }

    private fun generateNextRow(row: BooleanArray): BooleanArray {
        val arr = BooleanArray(row.size)
        arr[0] = row[1]
        arr[arr.lastIndex] = row[row.lastIndex - 1]
        for (i in 1..< row.lastIndex)
            arr[i] = row[i - 1] xor row[i + 1]
        return arr
    }
}
