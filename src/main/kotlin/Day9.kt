data class Point(val x: Int, val y: Int) {
    operator fun plus(point: Point): Point {
        return Point(x + point.x, y + point.y)
    }

    operator fun minus(point: Point): Point {
        return Point(x - point.x, y - point.y)
    }
}

enum class Direction(s: String) {
    UP("U"), DOWN("D"), LEFT("L"), RIGHT("R")

    
}

fun main() {
    var head = Point(0, 0)
    var tail = Point(0, 0)

    var visitedLocations = mutableSetOf<Point>()

    fileFromResource("day9/example.txt").forEachLine {
        val (direction, amount) = parseInstruction(it)
    }

}

fun parseInstruction(line: String): Pair<Direction, Int> {
    val parts = line.split(" ")
    val s = parts[0]
    val direction = Direction.valueOf(s)
    val amount = parts[1].toInt()

    return Pair(direction, amount)
}

