package days

@Suppress("unused")
object D04: Day {
    private val rooms: List<Pair<String, Int>>

    init {
        fun decryptName(encryptedName: String, sectorId: Int): String =
            buildString {
                for (c in encryptedName) {
                    if (c == '-') append(' ')
                    else append(((c - 'a' + sectorId) % 26 + 'a'.code).toChar())
                }
            }

        fun isRealRoom(encryptedName: String, checksum: String): Boolean {
            val arr = IntArray(26)
            for (c in encryptedName)
                if (c in 'a'..'z') arr[c - 'a']++
            val mostCommonCharacters = ('a'..'z').sortedByDescending { c ->  arr[c - 'a'] }
            return checksum.indices.all { i -> checksum[i] == mostCommonCharacters[i] }
        }

        rooms = buildList {
            readFile().forEach { line ->
                val lastDashIndex = line.indexOfLast { c -> c == '-' }
                val encryptedName = line.take(lastDashIndex)
                val sectorId = line.substring(lastDashIndex + 1, line.length - 7).toInt()
                val checksum = line.substring(line.length - 6, line.length - 1)
                if (isRealRoom(encryptedName, checksum)) add(decryptName(encryptedName, sectorId) to sectorId)
            }
        }
    }

    override suspend fun part1(): String = rooms.sumOf(Pair<String, Int>::second).toString()

    override suspend fun part2(): String = rooms.single { (name, _) ->
        "north" in name && "pole" in name && ("object" in name || "storage" in name)
    }.second.toString()
}
