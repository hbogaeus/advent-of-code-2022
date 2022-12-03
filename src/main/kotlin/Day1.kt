import java.util.Comparator
import java.util.PriorityQueue

fun main() {
    var sum = 0

    val priorityQueue = PriorityQueue<Int>(Comparator.reverseOrder())

    fileFromResource("day1/input.txt").forEachLine {
        if (it.isNotBlank()) {
            val toInt = it.toInt()
            sum += toInt
        } else {
            priorityQueue.add(sum)
            sum = 0
        }
    }

    var total = 0

    for (i in 1..3) {
        val poll = priorityQueue.poll()
        println(poll)
        total += poll
    }

    println(total)
}