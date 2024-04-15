package days

import java.util.LinkedList
import util.MD5Util

@Suppress("unused")
object D17 : Day {
    private val salt: String = readFile()[0]

    override fun part1(): String = getPathSequence().first()

    override fun part2(): String = getPathSequence().last().length.toString()

    private fun getPathSequence(): Sequence<String> =
        sequence {
            val seen = mutableSetOf("")
            val queue = LinkedList<Triple<Int, Int, String>>()
            queue.add(Triple(0, 0, ""))
            while (queue.isNotEmpty()) {
                val (x, y, path) = queue.poll()
                if (x == 3 && y == 3) yield(path)
                else for (dir in getValidDirections(path)) {
                    val nx = x + dir.dx
                    val ny = y + dir.dy
                    val nPath = path + dir.pathChar
                    if (nx in 0..3 && ny in 0..3 && nPath !in seen) {
                        seen.add(nPath)
                        queue.add(Triple(nx, ny, nPath))
                    }
                }
            }
        }

    private fun getValidDirections(path: String): List<Dir> =
        buildList {
            val hash = MD5Util.toMD5("$salt$path")
            if (hash[0] in "bcdef") add(Dir.UP)
            if (hash[1] in "bcdef") add(Dir.DOWN)
            if (hash[2] in "bcdef") add(Dir.LEFT)
            if (hash[3] in "bcdef") add(Dir.RIGHT)
        }

    private enum class Dir(val dx: Int, val dy: Int, val pathChar: Char) {
        UP(0, -1, 'U'),
        DOWN(0, 1, 'D'),
        LEFT(-1, 0, 'L'),
        RIGHT(1, 0, 'R'),
    }
}
