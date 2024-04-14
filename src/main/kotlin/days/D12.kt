package days

@Suppress("unused")
object D12 : Day {
    private val commands: List<Command> = readFile().map { s ->
        val splits = s.split(" ")
        when (splits[0]) {
            "inc" -> Increment(splits[1][0] - 'a')
            "dec" -> Decrement(splits[1][0]- 'a')
            "jnz" -> when (splits[1][0]) {
                in 'a'..'z' -> JumpIfNotZero(splits[1][0] - 'a', splits[2].toInt())
                '0' -> Dud
                else -> Jump(splits[2].toInt())
            }
            else -> if (splits[1][0] in 'a'..'z') CopyFromRegister(splits[2][0] - 'a', splits[1][0] - 'a') else Set(splits[2][0] - 'a', splits[1].toInt())
        }
    }

    override fun part1(): String = processCommands(IntArray(4)).toString()

    override fun part2(): String = processCommands(IntArray(4).apply { this[2] = 1 }).toString()

    private fun processCommands(registers: IntArray): Int {
        var p = 0
        while (p < commands.size) {
            when (val command = commands[p]) {
                is Increment -> { registers[command.register]++; p++ }
                is Decrement -> { registers[command.register]--; p++ }
                is Set -> { registers[command.register] = command.value; p++ }
                is CopyFromRegister -> { registers[command.register] = registers[command.from]; p++ }
                is JumpIfNotZero -> if (registers[command.register] != 0) p += command.offset else p++
                is Jump -> p += command.offset
                is Dud -> p++
            }
        }
        return registers[0]
    }

    private sealed interface Command
    private data class Increment(val register: Int): Command
    private data class Decrement(val register: Int): Command
    private data class Set(val register: Int, val value: Int): Command
    private data class CopyFromRegister(val register: Int, val from: Int): Command
    private data class JumpIfNotZero(val register: Int, val offset: Int): Command
    private data class Jump(val offset: Int): Command
    private data object Dud: Command
}
