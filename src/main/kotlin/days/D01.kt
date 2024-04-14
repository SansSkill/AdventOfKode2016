package days

import kotlin.math.abs

@Suppress("unused")
object D01 : Day {
    private val instructions = readFile()[0].split(", ")

    override fun part1(): String {
        var x = 0
        var y = 0
        var dir = Dir.North
        for (ins in instructions) {
            dir = if (ins[0] == 'R') dir.right() else dir.left()
            val scalar = ins.substring(1).toInt()
            x += dir.x * scalar
            y += dir.y * scalar
        }
        return abs(x).plus(abs(y)).toString()
    }

    override fun part2(): String {
        var x = 0
        var y = 0
        var dir = Dir.North
        val seen = mutableSetOf<Pair<Int, Int>>()
        for (ins in instructions) {
            dir = if (ins[0] == 'R') dir.right() else dir.left()
            val scalar = ins.substring(1).toInt()
            repeat(scalar) {
                x += dir.x
                y += dir.y
                if (x to y in seen) return@part2 abs(x).plus(abs(y)).toString()
                seen.add(x to y)
            }
        }
        error("Followed all instructions, did not visit any place twice")
    }

    private enum class Dir(val x: Int, val y: Int) {
        North(0, 1),
        East(1, 0),
        South(0, -1),
        West(-1, 0),
    }

    private fun Dir.right() = when (this) {
        Dir.North -> Dir.East
        Dir.East -> Dir.South
        Dir.South -> Dir.West
        Dir.West -> Dir.North
    }

    private fun Dir.left() = when (this) {
        Dir.North -> Dir.West
        Dir.West -> Dir.South
        Dir.South -> Dir.East
        Dir.East -> Dir.North
    }
}
