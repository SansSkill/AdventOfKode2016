package days

import java.util.LinkedList
import java.util.Queue

@Suppress("unused")
object D10 : Day {
    private val bots: List<Bot>
    private val startingValues: List<Pair<Int, Int>>

    init {
        val bots = mutableListOf<Bot>()
        val values = mutableListOf<Pair<Int, Int>>()
        readFile().asSequence()
            .map { s -> s.split(" ") }
            .forEach { splits ->
                if (splits[0] == "bot") { // bot A gives low to [bot|output] B and high to [bot|output] C
                    val number = splits[1].toInt()
                    val low = splits[6].toInt()
                    val lowOutputType = OutputType.fromString(splits[5])
                    val high = splits[11].toInt()
                    val highOutputType = OutputType.fromString(splits[10])
                    bots.add(Bot(number = number, low = low, lowOutputType = lowOutputType, high = high, highOutputType = highOutputType))
                } else { // value N goes to bot A
                    values.add(splits[5].toInt() to splits[1].toInt())
                }
            }
        this.bots = bots.sortedBy(Bot::number)
        startingValues = values
    }

    override fun part1(): String {
        val botsToProcess: Queue<Int> = resetBots()

        while (botsToProcess.isNotEmpty()) {
            val bot = bots[botsToProcess.poll()]
            if (17 in bot.heldNumbers && 61 in bot.heldNumbers) return bot.number.toString()
            while (bot.heldNumbers.size > 1) {
                val n = bot.heldNumbers.removeFirst()
                val m = bot.heldNumbers.removeFirst()
                bots[bot.low].heldNumbers.add(n.coerceAtMost(m))
                bots[bot.high].heldNumbers.add(n.coerceAtLeast(m))
                botsToProcess.add(bot.low)
                botsToProcess.add(bot.high)
            }
        }

        error("No bots left to process, did not find expected bot during processing")
    }

    override fun part2(): String {
        val botsToProcess: Queue<Int> = resetBots()
        val output = mutableMapOf<Int, Int>()

        while (botsToProcess.isNotEmpty()) {
            val bot = bots[botsToProcess.poll()]
            while (bot.heldNumbers.size > 1) {
                if (bot.heldNumbers.size > 2) println("HIT")
                val n = bot.heldNumbers.removeFirst()
                val m = bot.heldNumbers.removeFirst()

                val lowNumber = n.coerceAtMost(m)
                if (bot.lowOutputType == OutputType.BOT) {
                    bots[bot.low].heldNumbers.add(lowNumber)
                    botsToProcess.add(bot.low)
                } else output[bot.low] = lowNumber

                val highNumber = m.coerceAtLeast(n)
                if (bot.highOutputType == OutputType.BOT) {
                    bots[bot.high].heldNumbers.add(highNumber)
                    botsToProcess.add(bot.high)
                } else output[bot.high] = highNumber
            }
        }

        val output0 = output[0] ?: error("Output 0 not found")
        val output1 = output[1] ?: error("Output 1 not found")
        val output2 = output[2] ?: error("Output 2 not found")
        return (output0 * output1 * output2).toString()
    }

    private fun resetBots(): Queue<Int> {
        for (bot in bots) bot.heldNumbers.clear()
        startingValues.forEach { (botNumber, value) -> bots[botNumber].heldNumbers.add(value) }
        return bots.asSequence().filter { bot -> bot.heldNumbers.size > 1 }.mapTo(LinkedList(), Bot::number)
    }

    private data class Bot(
        val number: Int,
        val low: Int,
        val lowOutputType: OutputType,
        val high: Int,
        val highOutputType: OutputType,
        val heldNumbers: MutableList<Int> = mutableListOf()
    )

    private enum class OutputType {
        BOT, OUTPUT;

        companion object {
            fun fromString(s: String): OutputType = when (s) {
                "bot" -> BOT
                "output" -> OUTPUT
                else -> error("No mapping to OutputType found for input $s")
            }
        }
    }
}
