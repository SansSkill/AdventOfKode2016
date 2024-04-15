package days

import java.util.LinkedList

@Suppress("unused")
object D19 : Day {
    private val numberOfElves: Int = readFile()[0].toInt()

    override fun part1(): String {
        val queue = LinkedList<Int>()
        queue.addAll(1..numberOfElves)
        while (queue.size > 1) {
            queue.add(queue.poll())
            queue.poll()
        }
        return queue.poll().toString()
    }

    override fun part2(): String {
        val list = mutableListOf<Int>()
        list.addAll(1..numberOfElves)
        var p = 0
        while (list.size > 1) {
            val indexToRemove = (p + list.size / 2) % list.size
            list.removeAt(indexToRemove)
            if (indexToRemove > p) p++
            if (p == list.size) p = 0
        }
        return list[0].toString()
    }
}
