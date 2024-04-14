package days

import java.util.LinkedList

@Suppress("unused")
object D11 : Day {
    private val initialItems: List<Int>
    private val finalFloor: Int

    init {
        val regex = Regex("(\\w+)(-compatible)? (generator|microchip)")
        val microchips = mutableListOf<Pair<String, Int>>()
        val generators = mutableListOf<Pair<String, Int>>()
        val input = readFile()
        for (i in input.indices) {
            regex.findAll(input[i]).forEach { matchResult ->
                val pair = matchResult.groupValues[1] to i
                when (val type = matchResult.groupValues[3]) {
                    "generator" -> generators.add(pair)
                    "microchip" -> microchips.add(pair)
                    else -> error("Unexpected type $type")
                }
            }
        }

        initialItems = microchips.sortedBy(Pair<*, Int>::second).plus(generators.sortedBy(Pair<*, Int>::second)).map(Pair<*, Int>::second)
        finalFloor = input.lastIndex
    }

    override fun part1(): String = calculateMinimumSteps(initialItems).toString()

    override fun part2(): String {
        val items = buildList {
            addAll(initialItems.subList(0, initialItems.size / 2))
            add(0)
            add(0)
            addAll(initialItems.subList(initialItems.size / 2, initialItems.size))
            add(0)
            add(0)
        }
        return calculateMinimumSteps(items).toString()
    }

    private fun calculateMinimumSteps(items: List<Int>): Int {
        val dp = mutableSetOf(items to 0)
        val queue = LinkedList<State>()
        queue.add(State(items, 0, 0))
        while (true) {
            val state = queue.poll()
            if (state.floor == finalFloor && state.items.all { n -> n == finalFloor }) return state.steps
            for (newState in state.getNextStates()) {
                val pair = newState.items to newState.floor
                if (pair !in dp) {
                    dp.add(pair)
                    queue.add(newState)
                }
            }
        }
    }

    private data class State(
        val items: List<Int>,
        val floor: Int,
        val steps: Int,
    )

    private fun State.getNextStates(): List<State> {
        val arr = items.toIntArray()
        val itemCount = arr.size / 2
        val itemsOnCurrentFloor = items.indices.filter { i -> items[i] == floor }
        val nextStates = mutableListOf<State>()

        // For all microchips either the corresponding generator is on the same floor, or no generator is
        fun isValidConfiguration(): Boolean =
            (0..< itemCount).all { i ->
                arr[i + itemCount] == arr[i] || (itemCount .. arr.lastIndex).all { j -> j == i + itemCount || arr[j] != arr[i] }
            }

        // Via backtracking iterate over all possible next combinations
        if (floor != finalFloor) {
            itemsOnCurrentFloor.forEachIndexed { index, i ->
                arr[i]++
                if (isValidConfiguration()) nextStates.add(State(arr.toList(), floor = floor + 1, steps = steps + 1))
                (index + 1 .. itemsOnCurrentFloor.lastIndex).map(itemsOnCurrentFloor::get).forEach { j ->
                    arr[j]++
                    if (isValidConfiguration()) nextStates.add(State(arr.toList(), floor = floor + 1, steps = steps + 1))
                    arr[j]--
                }
                arr[i]--
            }
        }

        if (floor != 0) {
            itemsOnCurrentFloor.forEachIndexed { index, i ->
                arr[i]--
                if (isValidConfiguration()) nextStates.add(State(arr.toList(), floor = floor - 1, steps = steps + 1))
                (index + 1 .. itemsOnCurrentFloor.lastIndex).map(itemsOnCurrentFloor::get).forEach { j ->
                    arr[j]--
                    if (isValidConfiguration()) nextStates.add(State(arr.toList(), floor = floor - 1, steps = steps + 1))
                    arr[j]++
                }
                arr[i]++
            }
        }

        return nextStates
    }
}
