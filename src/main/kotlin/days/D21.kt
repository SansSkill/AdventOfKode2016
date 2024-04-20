package days

@Suppress("unused")
object D21 : Day {
    private val instructions: List<Instruction> = readFile().map { s ->
        val splits = s.split(" ")
        fun getInt(i: Int): Int = splits[i].toInt()
        fun getChar(i: Int): Char = splits[i][0]

        when (val command = splits[0]) {
            // swap letter $c1 with letter $c2
            // swap position $i1 with position $i2
            "swap" -> if (splits[1] == "position") SwapPosition(getInt(2), getInt(5)) else SwapLetter(getChar(2), getChar(5))
            // reverse positions $i1 through $i2
            "reverse" -> Reverse(getInt(2), getInt(4))
            // move position $from to position $to
            "move" -> Move(getInt(2), getInt(5))
            // rotate based on position of letter $char
            // rotate right $steps steps
            // rotate left $steps steps
            "rotate" -> when (splits[1]) {
                "left" -> RotateLeft(getInt(2))
                "right" -> RotateRight(getInt(2))
                else -> Rotate(getChar(6))
            }
            else -> error("Unexpected command $command")
        }
    }

    override fun part1(): String = scramble("abcdefgh")

    override fun part2(): String {
        val preScrambled = "fbgdceah"
        val sb = StringBuilder()
        val combinations = mutableListOf<String>()
        fun backtrack() {
            if (sb.length == 8) combinations.add(sb.toString())
            else {
                val c: Char = 'a' + sb.length
                val range = 0..sb.length
                for (i in range) {
                    sb.insert(i, c)
                    backtrack()
                    sb.deleteAt(i)
                }
            }
        }
        backtrack()
        return combinations.first { s -> scramble(s) == preScrambled }
    }

    private fun scramble(s: String) =
        buildString {
            append(s)
            instructions.forEach { instruction: Instruction -> apply(instruction) }
        }

    private fun StringBuilder.apply(instruction: Instruction) {
        fun rotateRight(steps: Int) {
            val cur = toString()
            for (i in indices) set(i, cur[(i - steps + 2 * length) % length])
        }

        when (instruction) {
            is SwapPosition -> {
                val tmp = get(instruction.i1)
                set(instruction.i1, get(instruction.i2))
                set(instruction.i2, tmp)
            }
            is SwapLetter -> {
                val i1 = indexOf(instruction.c1)
                val i2 = indexOf(instruction.c2)
                set(i1, instruction.c2)
                set(i2, instruction.c1)
            }
            is Reverse -> {
                val start = instruction.from
                val end = instruction.to + 1
                setRange(start, end, substring(start, end).reversed())
            }
            is RotateLeft -> {
                val cur = toString()
                for (i in indices) set(i, cur[(i + instruction.steps) % length])
            }
            is RotateRight -> rotateRight(instruction.steps)
            is Rotate -> {
                val index = indexOf(instruction.char)
                val steps = 1 + index + if (index >= 4) 1 else 0
                rotateRight(steps)
            }
            is Move -> {
                val c = get(instruction.i1)
                deleteAt(instruction.i1)
                insert(instruction.i2, c)
            }
        }
    }

    private sealed interface Instruction
    private data class SwapPosition(val i1: Int, val i2: Int): Instruction
    private data class SwapLetter(val c1: Char, val c2: Char): Instruction
    private data class Reverse(val from: Int, val to: Int): Instruction
    private data class RotateLeft(val steps: Int): Instruction
    private data class RotateRight(val steps: Int): Instruction
    private data class Rotate(val char: Char): Instruction
    private data class Move(val i1: Int, val i2: Int): Instruction
}
