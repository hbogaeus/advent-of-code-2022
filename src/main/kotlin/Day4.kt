fun main() {
    var runningSum = 0
    fileFromResource("day4/input.txt").forEachLine {
        val (a, b) = it.split(",")

        val (aStart, aEnd) = parse(a)
        val (bStart, bEnd) = parse(b)

        val aRange = aStart..aEnd
        val bRange = bStart..bEnd

        if ((aStart in bRange || aEnd in bRange) || (bStart in aRange || bEnd in aRange))
            runningSum++
    }

    println(runningSum)
}

private fun parse(a: String) = a.split('-').map { it.toInt() }