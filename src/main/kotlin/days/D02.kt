package days

@Suppress("unused")
object D02 : Day {
    private val instructions = readFile().map { line -> line.map(Dir::fromChar) }

    private fun getCode(keypad: List<List<Char>>): String =
        buildString {
            require(keypad.map(List<*>::size).toSet().size == 1) { "Keypad is not an N * M size matrix" }
            var y = keypad.indexOfFirst { row -> '5' in row }
            var x = keypad[y].indexOf('5')
            val yRange = keypad.indices
            val xRange = keypad[0].indices
            instructions.forEach { instruction ->
                instruction.forEach { direction ->
                    val newY = y + direction.y
                    val newX = x + direction.x
                    if (newY in yRange && newX in xRange && keypad[newY][newX] != '.') {
                        y = newY
                        x = newX
                    }
                }
                append(keypad[y][x])
            }
        }

    override suspend fun part1(): String {
        val keypad = listOf(
            listOf('1', '2', '3'),
            listOf('4', '5', '6'),
            listOf('7', '8', '9'),
        )
        return getCode(keypad)
    }

    override suspend fun part2(): String {
        val keypad = listOf(
            listOf('.', '.', '1', '.', '.'),
            listOf('.', '2', '3', '4', '.'),
            listOf('5', '6', '7', '8', '9'),
            listOf('.', 'A', 'B', 'C', '.'),
            listOf('.', '.', 'D', '.', '.'),
        )
        return getCode(keypad)
    }

    private enum class Dir(val x: Int, val y: Int) {
        Up(0, -1),
        Right(1, 0),
        Down(0, 1),
        Left(-1, 0);

        companion object {
            fun fromChar(char: Char): Dir =
                when (char) {
                    'U' -> Up
                    'R' -> Right
                    'D' -> Down
                    'L' -> Left
                    else -> error("Unexpected char $char")
                }
        }
    }
}