package days

import util.IntUtil.lcm

@Suppress("unused")
object D15 : Day {
    private val initialDiskPositions: IntArray
    private val initialDiskSizes: IntArray

    init {
        val input = readFile()
        initialDiskPositions = IntArray(input.size)
        initialDiskSizes = IntArray(input.size)
        for (i in input.indices) {
            val splits = input[i].split(" ")
            initialDiskPositions[i] = splits[11].dropLast(1).toInt()
            initialDiskSizes[i] = splits[3].toInt()
        }
        for (i in initialDiskPositions.indices) initialDiskPositions[i] = (initialDiskPositions[i] + i + 1) % initialDiskSizes[i]
    }

    override fun part1(): String = solveDisks(initialDiskPositions.copyOf(), initialDiskSizes.copyOf()).toString()

    override fun part2(): String {
        val diskPositions = initialDiskPositions.copyOf(initialDiskPositions.size + 1)
        val diskSizes = initialDiskSizes.copyOf(initialDiskSizes.size + 1)
        diskPositions[diskPositions.lastIndex] = diskPositions.size % 11
        diskSizes[diskSizes.lastIndex] = 11
        return solveDisks(diskPositions, diskSizes).toString()
    }

    private fun solveDisks(diskPositions: IntArray, diskSizes: IntArray): Int {
        var lcm = 1
        var diskToFix = 0
        var rotations = 0
        while (diskToFix < diskPositions.size) {
            for (i in diskPositions.indices) diskPositions[i] = (diskPositions[i] + lcm) % diskSizes[i]
            rotations += lcm
            if (diskPositions[diskToFix] == 0) {
                lcm = lcm.lcm(diskSizes[diskToFix])
                diskToFix++
            }
        }
        return rotations
    }
}
