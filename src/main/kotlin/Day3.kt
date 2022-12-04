const val LOWERCASE_OFFSET = 96
const val UPPERCASE_OFFSET = 38

fun main() {
    var runningSum = 0
    fileFromResource("day3/input.txt").useLines { lines ->
        lines.chunked(3).forEach { chunk ->
            val set1 = chunk[0].toSet()
            val set2 = chunk[1].toSet()
            val set3 = chunk[2].toSet()

            val intersection = set1 intersect set2 intersect set3

            if (intersection.size != 1) throw IllegalStateException("Size of intersection was ${intersection.size}")

            val char = intersection.elementAt(0)
            val priority = getPriority(char)

            runningSum += priority
        }
    }
    println(runningSum)
}

private fun problem1() {
    var runningSum = 0
    fileFromResource("day3/input.txt").forEachLine {
        val mid = it.length / 2

        val firstSet = it.substring(0, mid).toSet()
        val secondSet = it.substring(mid).toSet()

        val intersection = firstSet intersect secondSet

        if (intersection.size != 1) throw IllegalStateException("Size of intersection was ${intersection.size}")

        val char = intersection.elementAt(0)
        val priority = getPriority(char)

        runningSum += priority
    }

    println(runningSum)
}

fun getPriority(char: Char): Int {
    if (char in 'a'..'z')
        return char.code - LOWERCASE_OFFSET

    return char.code - UPPERCASE_OFFSET
}