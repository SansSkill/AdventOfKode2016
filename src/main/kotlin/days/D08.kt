package days

@Suppress("unused")
object D08 : Day {
    private val commands: List<Command> = readFile().map { line ->
        val splits = line.split(" ")
        if (splits[0] == "rect") {
            val (width, height) = splits[1].split("x")
            Rect(width.toInt(), height.toInt())
        } else {
            val rowOrColumn = splits[2].substring(2).toInt()
            val offset = splits[4].toInt()
            if (splits[1] == "row") RotateRow(rowOrColumn, offset) else RotateColumn(rowOrColumn, offset)
        }
    }

    private fun runCommands(): Array<BooleanArray> {
        val board = Array(6) { BooleanArray(50) }
        for (command in commands) {
            when (command) {
                is Rect -> for (y in 0..< command.height) for (x in 0..< command.width) board[y][x] = true
                is RotateRow -> {
                    val oldRow = board[command.row].copyOf()
                    for (i in oldRow.indices) board[command.row][i] = oldRow[(i + oldRow.size - command.offset) % oldRow.size]
                }
                is RotateColumn -> {
                    val oldColumn = BooleanArray(board.size) { i -> board[i][command.column]}
                    for (i in oldColumn.indices) board[i][command.column] = oldColumn[(i + oldColumn.size - command.offset) % oldColumn.size]
                }
            }
        }
        return board
    }

    override fun part1(): String =
        runCommands().sumOf { row -> row.count { b -> b } }.toString()

    override fun part2(): String =
        buildString {
            val board = runCommands()
            for (row in board) {
                for (b in row) append(if (b) '@' else '.')
                append("\n")
            }
            deleteAt(lastIndex)
        }

    private sealed interface Command
    private data class Rect(val width: Int, val height: Int): Command
    private data class RotateColumn(val column: Int, val offset: Int): Command
    private data class RotateRow(val row: Int, val offset: Int): Command
}
