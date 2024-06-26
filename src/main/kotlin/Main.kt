import days.Day
import kotlin.time.measureTime

fun main() {
    fun fromDayNumber(dayNumber: Int): Day? =
        try {
            val className = "days.D${dayNumber.toString().padStart(2, '0')}"
            Class.forName(className).kotlin.objectInstance as Day
        } catch (e: ClassNotFoundException) {
            null
        }

    fun print(day: Day) = try {
        val part1Time = measureTime { day.part1().let(::println) }.inWholeMilliseconds
        val part2Time = measureTime { day.part2().let(::println) }.inWholeMilliseconds
        println("Part 1 took $part1Time ms and part 2 took $part2Time ms")
    } catch (e: IllegalStateException) {
        println("IllegalStateException occurred during running: ${e.message}")
    }

    while (true) {
        println()
        print("Enter day to print: ")
        readln().trim().toIntOrNull()?.let { n ->
            fromDayNumber(n)?.let(::print) ?: println("Input is not a valid day")
        } ?: println("Input is not a valid number")
    }
}
