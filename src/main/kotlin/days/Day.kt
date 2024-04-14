package days

interface Day {
    @Suppress("SameReturnValue")
    fun part1(): String
    @Suppress("SameReturnValue")
    fun part2(): String

    @Suppress("unused")
    fun readFile(): List<String> {
        val fileName = "${this::class.simpleName}.txt"
        val resource = this::class.java.getResource(fileName) ?: error("Failed to read file $fileName")
        return resource.readText().split("\r\n")
    }
}
