package days

import java.util.LinkedList

@Suppress("unused")
object D13 : Day {
    private val n: Int = readFile()[0].toInt()

    override fun part1(): String {
        val dp = mutableSetOf(1 to 1)
        val queue = LinkedList<Triple<Int, Int, Int>>()
        queue.add(Triple(1, 1, 0))
        while (true) {
            val (x, y, steps) = queue.poll()
            if (x == 31 && y == 39) return steps.toString()
            for (xy in listOf(x + 1 to y, x - 1 to y, x to y - 1, x to y + 1)) {
                if (xy in dp) continue
                else if (!isOpen(xy.first, xy.second)) continue
                dp.add(xy)
                queue.add(Triple(xy.first, xy.second, steps + 1))
            }
        }
    }

    override fun part2(): String {
        val dp = mutableSetOf(1 to 1)
        val queue = LinkedList<Triple<Int, Int, Int>>()
        queue.add(Triple(1, 1, 0))
        while (queue.isNotEmpty()) {
            val (x, y, steps) = queue.poll()
            if (steps == 50) continue
            for (xy in listOf(x + 1 to y, x - 1 to y, x to y - 1, x to y + 1)) {
                if (xy in dp) continue
                else if (!isOpen(xy.first, xy.second)) continue
                dp.add(xy)
                queue.add(Triple(xy.first, xy.second, steps + 1))
            }
        }
        return dp.size.toString()
    }

    private fun isOpen(x: Int, y: Int): Boolean =
        x >= 0 && y >= 0 && (x*x + 3*x + 2*x*y + y + y*y + n).toString(2).count { c -> c == '1' } and 1 == 0
}
